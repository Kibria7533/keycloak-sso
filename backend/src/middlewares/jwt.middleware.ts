import {Inject, Injectable, NestMiddleware, UnauthorizedException} from '@nestjs/common';
import {NextFunction, Request, Response} from 'express';
import {verify} from 'jsonwebtoken';
import {KEYCLOAK_PUBLIC_KEY, KEYCLOAK_REALM} from "../utils/constants";
import {IdpService} from "../module/idp/idp.service";
import {AuthUser} from "./user/auth-user";

@Injectable()
export class JwtMiddleware implements NestMiddleware {
    @Inject()
    private readonly idpService: IdpService;

    async use(req: Request, res: Response, next: NextFunction) {
        const token = req.headers.authorization?.split(' ')[1];

        if (!token) throw new UnauthorizedException("Login Required")

        const decoded = verify(token, KEYCLOAK_PUBLIC_KEY, {algorithms: ['RS256']})
        const sub: any = decoded?.sub

        const user = await this.idpService.getIdpUserBySub(KEYCLOAK_REALM, sub)

        AuthUser.set(user)
        next()
    }
}
