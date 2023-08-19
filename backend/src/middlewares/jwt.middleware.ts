import {Inject, Injectable, NestMiddleware, NotFoundException} from '@nestjs/common';
import {NextFunction, Request, Response} from 'express';
import {verify} from 'jsonwebtoken';
import {AsyncLocalStorage} from "async_hooks";
import {CacheService} from "../module/cache/cache.service";
import {KEYCLOAK_HOST_URL, KEYCLOAK_PUBLIC_KEY, KEYCLOAK_REALM} from "../utils/constants";
import axios from "axios";

@Injectable()
export class JwtMiddleware implements NestMiddleware {
    @Inject()
    private readonly als: AsyncLocalStorage<any>

    @Inject()
    private cacheService: CacheService

    async use(req: Request, res: Response, next: NextFunction) {

        const token = req.headers.authorization?.split(' ')[1];
        if (token) {
            try {
                const adminAccessToken = await this.cacheService.getAdminCachedCredentials()
                if (!adminAccessToken) throw new NotFoundException('admin access token not found')

                const decoded = verify(token, KEYCLOAK_PUBLIC_KEY, {algorithms: ['RS256']})
                const userId = decoded?.sub

                const response = await axios.get(`${KEYCLOAK_HOST_URL}/admin/realms/${KEYCLOAK_REALM}/users/${userId}`, {
                    headers: {
                        Authorization: `Bearer ${adminAccessToken}`,
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                });

                const {data} = response

                this.als.run(data, () => {
                    next();
                });
            } catch (error) {
                console.log('jwt middleware error =>', error)
            }
        }
        next();
    }
}
