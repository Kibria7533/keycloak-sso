import {HttpException, Inject, Injectable} from "@nestjs/common";
import {HttpService} from "@nestjs/axios";
import {
    KEYCLOAK_ADMIN_PASSWORD,
    KEYCLOAK_ADMIN_USERNAME,
    KEYCLOAK_CLIENT_ID,
    KEYCLOAK_CLIENT_SECRET,
    KEYCLOAK_URL_GET_TOKEN,
    KEYCLOAK_URL_GET_USER
} from "../../utils/constants";
import {catchError, firstValueFrom, ObservableInput} from "rxjs";
import {map} from "rxjs/operators";

@Injectable()
export class IdpService {
    @Inject()
    private httpService: HttpService;

    async getIdpToken(realmName): Promise<any> {
        const url: string = KEYCLOAK_URL_GET_TOKEN.replace("{realm_name}", realmName);

        try {
            const response = await this.httpService.post(url, {
                    client_id: KEYCLOAK_CLIENT_ID,
                    client_secret: KEYCLOAK_CLIENT_SECRET,
                    grant_type: 'password',
                    username: KEYCLOAK_ADMIN_USERNAME,
                    password: KEYCLOAK_ADMIN_PASSWORD,
                },
                {
                    headers: this.getFormHeader()
                })

            const data = response.pipe(
                map((res) => res.data),
                catchError((error: any) => {
                    return this.throwError(error);
                }),
            );

            return await firstValueFrom(data)
        } catch (e) {
            console.log("getIdpTokenData => ", e)
            throw e
        }
    }

    async getIdpUserByUsername(realmName: string, username: string): Promise<any> {
        try {
            const tokenData = await this.getIdpToken(realmName);
            let url: string = KEYCLOAK_URL_GET_USER.replace("{realm_name}", realmName);

            const params: any = {
                username: username
            }

            const response = await this.httpService
                .get(url, {
                    params: new URLSearchParams(params),
                    headers: this.getHeader(tokenData)
                })

            const data = response.pipe(
                map((res) => res.data),
                catchError((error: any) => {
                    return this.throwError(error);
                }),
            );

            return await firstValueFrom(data)
        } catch (e) {
            console.log("getIdpUserByUsername => ", e)
            throw e
        }
    }

    async getIdpUserBySub(realmName: string, sub: string): Promise<any> {
        try {
            const tokenData = await this.getIdpToken(realmName);
            let url: string = KEYCLOAK_URL_GET_USER.replace("{realm_name}", realmName);
            url = url + `/${sub}`

            const response = await this.httpService
                .get(url, {
                    headers: this.getHeader(tokenData)
                })

            const data = response.pipe(
                map((res) => res.data),
                catchError((error: any) => {
                    return this.throwError(error);
                }),
            );

            return await firstValueFrom(data)
        } catch (e) {
            console.log("getIdpUserByUsername => ", e)
            throw e
        }
    }

    throwError(error): ObservableInput<any> {
        console.log("IDP catch Error-->", error.response);
        // this.logger.error(error);

        if (error?.response) {
            throw new HttpException(new Error(error.response?.statusText + " in IDP"), error.response.status);
        }
        throw new HttpException(new Error("Something wrong in IDP"), 500);
    }

    getHeader(tokenData?): any {
        const withoutToken = {
            "Content-Type": "application/json",
            Accept: "application/json",
        }
        const withToken = {
            "Content-Type": "application/json",
            Accept: "application/json",
            Authorization: `Bearer ${tokenData?.access_token}`,
        }


        return tokenData ? withToken : withoutToken
    }

    getFormHeader(tokenData?): any {
        const withoutToken = {
            "Content-Type": "application/x-www-form-urlencoded",
        }
        const withToken = {
            "Content-Type": "application/x-www-form-urlencoded",
            Authorization: `Bearer ${tokenData?.access_token}`,
        }


        return tokenData ? withToken : withoutToken
    }
}