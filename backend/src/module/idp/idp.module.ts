import {Module} from "@nestjs/common";
import {IdpService} from "./idp.service";
import {HttpModule} from "@nestjs/axios";
import {IdpController} from "./idp.controller";

@Module({
    imports: [HttpModule],
    controllers: [IdpController],
    providers: [IdpService],
    exports: [IdpService]
})

export class IdpModule {}