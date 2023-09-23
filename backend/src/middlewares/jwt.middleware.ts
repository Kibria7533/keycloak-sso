import {Inject, Injectable, NestMiddleware, NotFoundException, UnauthorizedException} from '@nestjs/common';
import {NextFunction, Request, Response} from 'express';
import {verify} from 'jsonwebtoken';
import {KEYCLOAK_DEFAULT_USER_ROLE, KEYCLOAK_PUBLIC_KEY, KEYCLOAK_REALM} from "../utils/constants";
import {IdpService} from "../module/idp/idp.service";
import {AuthUser} from "./user/auth-user";

@Injectable()
export class JwtMiddleware implements NestMiddleware {
    @Inject()
    private readonly idpService: IdpService;

    async use(req: Request, res: Response, next: NextFunction) {
        const adminToken = await this.idpService.getIdpToken(KEYCLOAK_REALM);
        const token = req.headers.authorization?.split(' ')[1];

        if (!token) throw new UnauthorizedException("Login Required")

        const decoded = verify(token, KEYCLOAK_PUBLIC_KEY, {algorithms: ['RS256']})
        const sub: any = decoded?.sub

        const {
            username,
            firstName,
            lastName,
            email,
            id
        } = await this.idpService.getIdpUserBySub(KEYCLOAK_REALM, sub, adminToken)

        let user = {
            id, username, firstName, lastName, email, roles: []
        }
        AuthUser.set(user)

        const allGroups = await this.idpService.getIdpGroups(adminToken)

        const userGroup = allGroups.filter((group) => group.name == KEYCLOAK_DEFAULT_USER_ROLE)
        if (!userGroup) throw new NotFoundException('User Group not found')

        const userGroupId = userGroup[0].id
        const groupRoles = await this.idpService.getIdpGroupRoles(userGroupId)
        if (!groupRoles) throw new NotFoundException('Groups role not found')

        const realmRole = groupRoles?.realmMappings[0]?.name;
        // const clientRole = groupRoles?.clientMappings[KEYCLOAK_CLIENT_ID]?.mappings[0].name;

        AuthUser.addRole(realmRole)

        next()
    }
}
