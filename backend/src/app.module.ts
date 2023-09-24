import {MiddlewareConsumer, Module, RequestMethod} from '@nestjs/common';
import {AppController} from './app.controller';
import {AppService} from './app.service';
import {AuthModule} from "./module/auth/auth.module";
import {APP_INTERCEPTOR} from "@nestjs/core";
import {ResponseInterceptor} from "./interceptor/response.interceptor";
import {JwtMiddleware} from "./middlewares/jwt.middleware";
import {IdpModule} from "./module/idp/idp.module";

@Module({
    imports: [AuthModule, IdpModule],
    controllers: [AppController],
    providers: [AppService,
        {
            provide: APP_INTERCEPTOR,
            useClass: ResponseInterceptor,
        }
    ]
})

export class AppModule {
    configure(consumer: MiddlewareConsumer) {
        consumer
            .apply(JwtMiddleware)
            .forRoutes(
                {path: '/auth/profile', method: RequestMethod.ALL},
                {path: '/idp/profile', method: RequestMethod.GET}
            );
    }

}
