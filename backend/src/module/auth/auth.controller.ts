import {Body, Controller, Get, Inject, Post, Req, Res} from "@nestjs/common";
import {AuthService} from "./auth.service";
import {Request, Response} from "express";
import {ApiBearerAuth, ApiTags} from "@nestjs/swagger";
import {CreateUserDto} from "../idp/dtos/createUser.dto";

@ApiTags("auth")
@ApiBearerAuth()
@Controller('auth')
export class AuthController {
    @Inject()
    private readonly authService: AuthService;

    @Get('profile')
    async getProfile() {
        return this.authService.getProfile();
    }

    @Post('token')
    async getKeycloakAuthToken(@Req() req: Request, @Res() res: Response) {
        return this.authService.getKeycloakAuthToken(req, res);
    }


}