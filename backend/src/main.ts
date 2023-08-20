import {NestFactory} from '@nestjs/core';
import {AppModule} from './app.module';
import * as dotenv from 'dotenv';
import {SwaggerModule} from "@nestjs/swagger";
import swaggerConfig from "./config/swagger.config";


async function bootstrap() {

    dotenv.config();

    const app = await NestFactory.create(AppModule);

    app.enableCors()

    const document = SwaggerModule.createDocument(app, swaggerConfig);
    SwaggerModule.setup("/", app, document);

    await app.listen(5000);
}

bootstrap();
