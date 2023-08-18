import {AuthController} from "./auth.controller";
import {Module} from "@nestjs/common";
import {FrontendBackChannelAppService} from "./auth.service";


@Module({
    controllers: [AuthController],
    providers: [FrontendBackChannelAppService],
})
export class AuthModule {}
