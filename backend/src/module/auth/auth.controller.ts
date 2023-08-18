import {Controller, Inject, Post, Req, Res} from "@nestjs/common";
import {FrontendBackChannelAppService} from "./auth.service";



@Controller('auth')
export class AuthController {
    @Inject()
    private readonly frontendBackChannelAppService: FrontendBackChannelAppService;

    @Post('token')
    async getKeycloakAuthToken(@Req() req:any, @Res() res:any) {
        return  this.frontendBackChannelAppService.getKeycloakAuthToken(req, res);
    }
}