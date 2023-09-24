import {
  HttpException,
  HttpStatus,
  Inject,
  Injectable,
  NotFoundException,
} from '@nestjs/common';
import { HttpService } from '@nestjs/axios';
import {
  KEYCLOAK_ADMIN_PASSWORD,
  KEYCLOAK_ADMIN_USERNAME,
  KEYCLOAK_CLIENT_ID,
  KEYCLOAK_CLIENT_SECRET,
  KEYCLOAK_CREATE_USER_PASSWORD_TYPE,
  KEYCLOAK_DEFAULT_USER_GROUP,
  KEYCLOAK_GET_ALL_GROUPS,
  KEYCLOAK_GET_GROUP_ROLES,
  KEYCLOAK_REALM,
  KEYCLOAK_URL_GET_TOKEN,
  KEYCLOAK_USER_URL,
} from '../../utils/constants';
import {
  catchError,
  firstValueFrom,
  lastValueFrom,
  ObservableInput,
} from 'rxjs';
import { map } from 'rxjs/operators';
import { AuthUser } from '../../middlewares/user/auth-user';

@Injectable()
export class IdpService {
  @Inject()
  private httpService: HttpService;

  async getIdpToken(realmName): Promise<any> {
    const url: string = KEYCLOAK_URL_GET_TOKEN.replace(
      '{realm_name}',
      realmName,
    );

    try {
      const response = this.httpService.post(
        url,
        {
          client_id: KEYCLOAK_CLIENT_ID,
          client_secret: KEYCLOAK_CLIENT_SECRET,
          grant_type: 'password',
          username: KEYCLOAK_ADMIN_USERNAME,
          password: KEYCLOAK_ADMIN_PASSWORD,
        },
        {
          headers: this.getFormHeader(),
        },
      );

      const data = response.pipe(
        map((res) => res.data),
        catchError((error: any) => {
          return this.throwError(error);
        }),
      );

      return await firstValueFrom(data);
    } catch (e) {
      console.log('getIdpTokenData => ', e);
      throw e;
    }
  }

  async getIdpUserByUsername(
    realmName: string,
    username: string,
    tokenData?: string,
  ): Promise<any> {
    try {
      if (!tokenData) tokenData = await this.getIdpToken(realmName);

      let url: string = KEYCLOAK_USER_URL.replace('{realm_name}', realmName);

      const params: any = {
        username,
      };

      const response = this.httpService.get(url, {
        params: new URLSearchParams(params),
        headers: this.getHeader(tokenData),
      });

      const data = response.pipe(
        map((res) => res.data),
        catchError((error: any) => {
          return this.throwError(error);
        }),
      );

      return await firstValueFrom(data);
    } catch (e) {
      console.log('getIdpUserByUsername => ', e);
      throw e;
    }
  }

  async getIdpUserBySub(
    realmName: string,
    sub: string,
    tokenData?: string,
  ): Promise<any> {
    try {
      if (!tokenData) tokenData = await this.getIdpToken(realmName);
      let url: string = KEYCLOAK_USER_URL.replace('{realm_name}', realmName);
      url = url + `/${sub}`;

      const response = this.httpService.get(url, {
        headers: this.getHeader(tokenData),
      });

      const data = response.pipe(
        map((res) => res.data),
        catchError((error: any) => {
          return this.throwError(error);
        }),
      );

      return await firstValueFrom(data);
    } catch (e) {
      console.log('getIdpUserByUsername Error=> ', e);
      throw e;
    }
  }

  async createIdpUser(idpCreatedReqData) {
    try {
      const adminToken = await this.getIdpToken(KEYCLOAK_REALM);
      let idpUser = null;
      const { username, firstName, lastName, address, email, password } =
        idpCreatedReqData;

      const payload = {
        username,
        firstName,
        lastName,
        email,
        emailVerified: true,
        enabled: true,
        credentials: [
          {
            type: KEYCLOAK_CREATE_USER_PASSWORD_TYPE,
            value: password,
            temporary: false,
          },
        ],
        attributes: {
          address,
        },
        groups: [KEYCLOAK_DEFAULT_USER_GROUP],
      };
      let url: string = KEYCLOAK_USER_URL.replace(
        '{realm_name}',
        KEYCLOAK_REALM,
      );

      const observable = this.httpService
        .post(url, payload, {
          headers: this.getHeader(adminToken),
        })
        .pipe(
          map((res) => res),
          catchError((error: any) => {
            if (!error.response.data) {
              throw new HttpException(new Error('Something wrong in IDP'), 500);
            }
            let errorMessage = error.response.data.errorMessage
              ? error.response.data.errorMessage
              : error.response.data.error;

            throw new HttpException(
              new Error(errorMessage + ' in IDP'),
              error.response.status,
            );
          }),
        );

      const idpResponse = await lastValueFrom(observable);
      if (idpResponse.status == HttpStatus.CREATED) {
        idpUser = await this.getIdpUserByUsername(
          KEYCLOAK_REALM,
          idpCreatedReqData.username,
          adminToken,
        );
      }

      return idpUser;
    } catch (e: any) {
      console.log('Error =>', e.message);
    }
  }

  async getProfile() {
    const user = AuthUser.get();

    if (!user) throw new NotFoundException('Profile not found');
    return user;
  }

  async getIdpGroups(tokenData?: string) {
    try {
      if (!tokenData) tokenData = await this.getIdpToken(KEYCLOAK_REALM);
      let url: string = KEYCLOAK_GET_ALL_GROUPS.replace(
        '{realm_name}',
        KEYCLOAK_REALM,
      );

      const response = this.httpService.get(url, {
        headers: this.getHeader(tokenData),
      });

      const data = response.pipe(
        map((res) => res.data),
        catchError((error: any) => {
          return this.throwError(error);
        }),
      );

      return firstValueFrom(data);
    } catch (e) {
      console.log('Get All Groups Error =>', e.message);
      throw e;
    }
  }

  async getIdpGroupRoles(groupId: string, tokenData?: string) {
    try {
      if (!tokenData) tokenData = await this.getIdpToken(KEYCLOAK_REALM);
      let url: string = KEYCLOAK_GET_GROUP_ROLES.replace('{GROUP_ID}', groupId);

      const response = this.httpService.get(url, {
        headers: this.getHeader(tokenData),
      });

      const data = response.pipe(
        map((res) => res.data),
        catchError((error: any) => {
          console.log(error);
          let errorMessage = error.data.errorMessage
            ? error.data.errorMessage
            : error.data.error;
          return this.throwError(error);
        }),
      );

      return firstValueFrom(data);
    } catch (e) {
      console.log('Get All Groups Error =>', e.message);
      throw e;
    }
  }

  throwError(error): ObservableInput<any> {
    console.log('IDP catch Error-->', error.response);
    // this.logger.error(error);

    if (error?.response) {
      throw new HttpException(
        new Error(error.response?.statusText + ' in IDP'),
        error.response.status,
      );
    }
    throw new HttpException(new Error('Something wrong in IDP'), 500);
  }

  getHeader(tokenData?: any): any {
    const withoutToken = {
      'Content-Type': 'application/json',
      Accept: 'application/json',
    };
    const withToken = {
      'Content-Type': 'application/json',
      Accept: 'application/json',
      Authorization: `Bearer ${tokenData?.access_token}`,
    };

    return tokenData ? withToken : withoutToken;
  }

  getFormHeader(tokenData?): any {
    const withoutToken = {
      'Content-Type': 'application/x-www-form-urlencoded',
    };
    const withToken = {
      'Content-Type': 'application/x-www-form-urlencoded',
      Authorization: `Bearer ${tokenData?.access_token}`,
    };

    return tokenData ? withToken : withoutToken;
  }
}
