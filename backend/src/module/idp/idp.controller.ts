import {Controller, Get, Inject, Param} from "@nestjs/common";
import {IdpService} from "./idp.service";
import {KEYCLOAK_REALM} from "../../utils/constants";
import {ApiBearerAuth, ApiTags} from "@nestjs/swagger";

@ApiTags("idp")
@ApiBearerAuth()
@Controller('idp')
export class IdpController {
    @Inject()
    private readonly idpService: IdpService;

    @Get('getAdminToken')
    async getToken() {
        return this.idpService.getIdpToken(KEYCLOAK_REALM)
    }

    @Get('getUserByUsername/:username')
    async getUser(@Param("username") username: string) {
        return this.idpService.getIdpUserByUsername(KEYCLOAK_REALM, username)
    }

    @Get('getUserBySub/:sub')
    async getUserBySub(@Param("sub") sub: string) {
        return this.idpService.getIdpUserBySub(KEYCLOAK_REALM, sub)
    }
}
