import {FrontendBackChannelController} from "./frontend.back.channel.controller";
import {Module} from "@nestjs/common";
import {FrontendBackChannelAppService} from "./frontend.back.channel.service";


@Module({
    controllers: [FrontendBackChannelController],
    providers: [FrontendBackChannelAppService],
})
export class FrontendBackChannelModule {}
