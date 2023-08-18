import {Injectable, NotFoundException} from '@nestjs/common';
import axios from 'axios';
import * as process from "process";


@Injectable()
export class FrontendBackChannelAppService {
    constructor() {
    }

    getKeycloakTokenURL = () => {
        return `${process.env.APP_KEYCLOAK_HOST}/realms/${process.env.APP_KEYCLOAK_REALM}/protocol/openid-connect/token`;
    }

    async getKeycloakAuthToken(req: any, res: any) {

        const params = {
            grant_type: 'authorization_code',
            client_id: process.env.APP_KEYCLOAK_CLIENT_ID,
            client_secret: process.env.APP_KEYCLOAK_CLIENT_SECRET,
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
            if (e.response) {
                console.log('Response Data:', e.response.data);
                console.log('Response Status:', e.response.status);
            } else {
                console.log('Error:', e.message);
            }
            throw new NotFoundException(e.message);
        }
    }


}
