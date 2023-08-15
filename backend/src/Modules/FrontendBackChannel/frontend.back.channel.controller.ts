import {Controller, Inject, Post, Req, Res} from "@nestjs/common";
import {FrontendBackChannelAppService} from "./frontend.back.channel.service";



@Controller('public/back-channel')
export class FrontendBackChannelController  {
    @Inject()
    private readonly frontendBackChannelAppService: FrontendBackChannelAppService;

    @Post('keycloak-auth-token')
    async getKeycloakAuthToken(@Req() req:any, @Res() res:any) {

        return  this.frontendBackChannelAppService.getKeycloakAuthToken(req, res);
    }
}