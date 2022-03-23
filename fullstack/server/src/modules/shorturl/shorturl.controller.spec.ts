import { Test, TestingModule } from '@nestjs/testing';
import { RedisModule } from '@nestjs-modules/ioredis';
import { TypeOrmModule } from '@nestjs/typeorm';
import { ShorturlController } from './shorturl.controller';
import { ShorturlService } from './shorturl.service';
import { ShorturlEntity } from './shorturl.entity';
import { DatabaseConfigModule } from '../config/database.module';
import { RedisConfigModule } from '../config/redis.module';

describe('ShorturlController', () => {
  let shorturlController: ShorturlController;

  beforeEach(async () => {
    const app: TestingModule = await Test.createTestingModule({
      imports: [
        DatabaseConfigModule,
        RedisConfigModule,
        TypeOrmModule.forFeature([ShorturlEntity]),
      ],
      controllers: [ShorturlController],
      providers: [ShorturlService],
    }).compile();

    shorturlController = app.get<ShorturlController>(ShorturlController);
  });


  it('Controller should return url of the shorturl', async () => {
    const s_url = '8C1AAA5F'
    const url = 'http://localhost:3332/api/swagger/'

    const res2 = await shorturlController.getlong({
      s_url
    });

    expect(res2).toEqual({"message": "链接不存在", "statusCode": 404})
  });

  // it('Controller should return s_url of the shorturl', async () => {
  //   const s_url = '8C1AAA5F'
  //   const url = 'http://localhost:3332/api/swagger/'

  //   const res1 = await shorturlController.getshort({
  //     url
  //   });
  //   expect(res1).toEqual(s_url)
  // });
});
