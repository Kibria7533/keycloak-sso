import {Body, Controller, Get, Inject, Param, Post} from "@nestjs/common";
import {IdpService} from "./idp.service";
import {KEYCLOAK_REALM} from "../../utils/constants";
import {ApiBearerAuth, ApiTags} from "@nestjs/swagger";
import {CreateUserDto} from "./dtos/createUser.dto";

@ApiTags("idp")
@ApiBearerAuth()
@Controller('idp')
export class IdpController {
    @Inject()
    private readonly idpService: IdpService;

    @Post('createUser')
    async createUser(@Body() body:CreateUserDto) {
        return this.idpService.createIdpUser(body)
    }

    @Get('getAdminToken')
    async getToken() {
        return this.idpService.getIdpToken(KEYCLOAK_REALM)
    }

    @Get('getUserByUsername/:username')
    async getUser(@Param("username") username: string) {
        return this.idpService.getIdpUserByUsername(KEYCLOAK_REALM, username)
    }

    @Get('profile')
    async getProfile() {
        return this.idpService.getProfile();
    }
    @Get('getUserBySub/:sub')
    async getUserBySub(@Param("sub") sub: string) {
        return this.idpService.getIdpUserBySub(KEYCLOAK_REALM, sub)
    }

    @Get('getAllGroups')
    async getIdpGroups() {
        return this.idpService.getIdpGroups()
    }
}
