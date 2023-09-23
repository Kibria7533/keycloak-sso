import {AuthController} from "./auth.controller";
import {Module} from "@nestjs/common";
import {AuthService} from "./auth.service";
import {IdpService} from "../idp/idp.service";
import {IdpModule} from "../idp/idp.module";
import {HttpModule} from "@nestjs/axios";


@Module({
    imports:[IdpModule,HttpModule],
    controllers: [AuthController],
    providers: [AuthService],
})
export class AuthModule {}
