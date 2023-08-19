import {Inject, Injectable, NotFoundException} from '@nestjs/common';
import axios from 'axios';
import {AsyncLocalStorage} from "async_hooks";
import {KEYCLOAK_CLIENT_ID, KEYCLOAK_CLIENT_SECRET, KEYCLOAK_HOST_URL, KEYCLOAK_REALM} from "../../utils/constants";


@Injectable()
export class AuthService {

    @Inject()
    private readonly als: AsyncLocalStorage<any>


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

    async getUser(req: any, res: any) {
        try {
            const user = this.als.getStore();
            if (!user) throw new NotFoundException("Profile data not found")
            // console.log('user profile =>', user)
            return user
        } catch (error) {
            console.log('Profile data error =>', error)
            throw error
        }

    }


}
