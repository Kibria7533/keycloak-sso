import {Controller, Get, Inject, Post, Req, Res} from "@nestjs/common";
import {AuthService} from "./auth.service";
import {Request, Response} from "express";

@Controller('auth')
export class AuthController {
    @Inject()
    private readonly authService: AuthService;

    @Get('user')
    async getUserInfo(@Req() req: Request, @Res() res: Response) {
        return this.authService.getUser(req, res);

    }

    @Post('token')
    async getKeycloakAuthToken(@Req() req: Request, @Res() res: Response) {
        return this.authService.getKeycloakAuthToken(req, res);
    }


}