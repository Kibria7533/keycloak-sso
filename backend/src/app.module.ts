import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import {FrontendBackChannelModule} from "./modules/FrontendBackChannel/frontend.back.channel.module";
import {APP_INTERCEPTOR} from "@nestjs/core";
import {ResponseInterceptor} from "./interceptor/response.interceptor";

@Module({
  imports: [FrontendBackChannelModule],
  controllers: [AppController],
  providers: [AppService,  {
    provide: APP_INTERCEPTOR,
    useClass: ResponseInterceptor,
  },],
})
export class AppModule {}
