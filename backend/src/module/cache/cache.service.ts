import {Inject, Injectable} from '@nestjs/common';
import {Cache} from 'cache-manager';
import axios from 'axios';
import {
    KEYCLOAK_ADMIN_PASSWORD,
    KEYCLOAK_ADMIN_USERNAME,
    KEYCLOAK_CLIENT_ID,
    KEYCLOAK_CLIENT_SECRET,
    KEYCLOAK_HOST_URL,
    KEYCLOAK_REALM
} from "../../utils/constants";
import {CACHE_MANAGER} from "@nestjs/cache-manager";

@Injectable()
export class CacheService {
    @Inject(CACHE_MANAGER)
    private readonly cache: Cache

    async getAdminCachedCredentials(): Promise<any> {
        try {
            const adminToken = await this.cache.get('accesstoken');

            if (adminToken) {
                return adminToken;
            }

            const response = await axios.post(`${KEYCLOAK_HOST_URL}/realms/${KEYCLOAK_REALM}/protocol/openid-connect/token`, {
                client_id: KEYCLOAK_CLIENT_ID,
                client_secret: KEYCLOAK_CLIENT_SECRET,
                grant_type: 'password',
                username: KEYCLOAK_ADMIN_USERNAME,
                password: KEYCLOAK_ADMIN_PASSWORD,
            }, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
            });

            const {access_token} = response.data;
            await this.cache.set('accesstoken', access_token);
            return access_token;
        } catch (error) {
            console.error('Error getting admin credentials:', error.message);
            throw error;
        }
    }
}
