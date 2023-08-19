import {Global, Module} from '@nestjs/common';
import { CacheModule as NestCacheModule} from '@nestjs/cache-manager';
import {CacheService} from "./cache.service";



@Module({
    imports: [
        NestCacheModule.register({
            store: 'memory',
            max: 100,
            ttl: 30000,
        }),
    ],
    providers: [CacheService],
    exports: [CacheService],
})
export class CacheModule {}
