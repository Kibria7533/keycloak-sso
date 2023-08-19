import { Module, Global } from '@nestjs/common';
import { AsyncLocalStorage } from 'async_hooks';

@Global()
@Module({
    providers: [
        {
            provide: AsyncLocalStorage,
            useValue: new AsyncLocalStorage(),
        },
    ],
    exports: [AsyncLocalStorage],
})
export class AlsModule {}
