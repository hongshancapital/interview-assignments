import { Module } from '@nestjs/common';

import { AppController } from './api/app.controller';
import { DB_SERVICE_TOKEN, DbService } from './db/db.service';
import { AppService } from './service/app.service';
import { ConfigService } from './service/config.service';
import { UrlRepository } from './service/url.repository';

@Module({
  imports: [],
  controllers: [AppController],
  providers: [
    AppService,
    ConfigService,
    UrlRepository,
    {
      provide: DB_SERVICE_TOKEN,
      useFactory: async (config: ConfigService) => {
        const dbSrv = new DbService(config);
        await dbSrv.init();

        return dbSrv;
      },
      inject: [ConfigService],
    },
  ],
})
export class AppModule {}
