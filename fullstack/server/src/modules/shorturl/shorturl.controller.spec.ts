import { Test, TestingModule } from '@nestjs/testing';
import { RedisModule } from '@nestjs-modules/ioredis';
import { TypeOrmModule } from '@nestjs/typeorm';
import { ShorturlController } from './shorturl.controller';
import * as ormconfig from '../../../ormconfig';
import { ShorturlService } from './shorturl.service';
import { ShorturlEntity } from './shorturl.entity';

describe('ShorturlController', () => {
  let shorturlController: ShorturlController;

  beforeEach(async () => {
    const app: TestingModule = await Test.createTestingModule({
      imports: [
        TypeOrmModule.forRoot(),
        TypeOrmModule.forFeature([ShorturlEntity]),
        RedisModule.forRootAsync({
          useFactory: () => ({
            config: { 
              name: ormconfig.database,
              host: ormconfig.host,
              port: 6379,
              password: ormconfig.password,
            },
          }),
        }),
      ],
      controllers: [ShorturlController],
      providers: [ShorturlService],
    }).compile();

    shorturlController = app.get<ShorturlController>(ShorturlController);
  });


  it('Controller should return url of the shorturl', async () => {
    const s_url = '8C1AAA5F'
    const url = 'http://localhost:3332/api/swagger/'

    const res2 = await shorturlController.findUrl({
      s_url
    });

    expect(res2).toEqual(url)
  });

  // it('Controller should return s_url of the shorturl', async () => {
  //   const s_url = '8C1AAA5F'
  //   const url = 'http://localhost:3332/api/swagger/'

  //   const res1 = await shorturlController.findSUrl({
  //     url
  //   });
  //   expect(res1).toEqual(s_url)
  // });
});
