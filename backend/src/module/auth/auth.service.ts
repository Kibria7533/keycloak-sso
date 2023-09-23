import {Inject, Injectable, NotFoundException} from '@nestjs/common';
import axios from 'axios';
import {
    KEYCLOAK_CLIENT_ID,
    KEYCLOAK_CLIENT_SECRET,
    KEYCLOAK_DEFAULT_USER_ROLE,
    KEYCLOAK_HOST_URL,
    KEYCLOAK_REALM
} from "../../utils/constants";
import {AuthUser} from "../../middlewares/user/auth-user";
import {HttpService} from "@nestjs/axios";
import {IdpService} from "../idp/idp.service";


@Injectable()
export class AuthService {


    @Inject()
    private httpService: HttpService;
    private idpService: IdpService;

    getKeycloakTokenURL = () => {
        return `${KEYCLOAK_HOST_URL}/realms/${KEYCLOAK_REALM}/protocol/openid-connect/token`;
    }

    async getKeycloakAuthToken(req: any, res: any) {

        const params = {
            grant_type: 'authorization_code',
            client_id: KEYCLOAK_CLIENT_ID,
            client_secret: KEYCLOAK_CLIENT_SECRET,
            redirect_uri: req.body.redirect_uri,
            code: req.body.code,
        }
        try {

            const response = await axios.post(
                this.getKeycloakTokenURL(),
                new URLSearchParams(params),
                {
                    headers: {
                        'content-type': 'application/x-www-form-urlencoded',
                    },
                },
            );

            return res.status(200).json(response.data);
        } catch (e: any) {
            console.log('Error:', e.message);
            throw new NotFoundException(e.message);
        }
    }

    async getProfile() {
        const user = AuthUser.get()

        const adminToken =await this.idpService.getIdpToken(KEYCLOAK_REALM);
        // const allGroups=await this.idpService.getIdpGroups(adminToken)
        // const defaultUserGroupId=allGroups.filter((group)=>group.name==KEYCLOAK_DEFAULT_USER_ROLE)
        //
        // console.log('default group =>', defaultUserGroupId)


        console.log(user)
        if (!user) throw new NotFoundException("Profile not found")
        return user
    }
}
