import { Test, TestingModule } from '@nestjs/testing';
import { TypeOrmModule } from '@nestjs/typeorm';
import { ShortEntity } from '../../entities/short.entity';
import { CacheModule } from '../cache/cache.module';
import { RedisModule } from '../redis/redis.module';
import { ShortController } from './short.controller';
import { ShortService } from './short.service';
import { config } from '../../common/config';
import { RedisShortService } from '../redis/redis.short.service';
import { randomShortCode } from '../../common/util';
import { HttpException } from '@nestjs/common';

describe('ShortController', () => {
  let shortController: ShortController;
  let shortService: ShortService;
  let redisShortService: RedisShortService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      imports: [
        // @ts-ignore
        TypeOrmModule.forRoot({
          ...config.database,
          entities: [ShortEntity],
          autoLoadEntities: true, // 自动加载实体类
        }),
        CacheModule,
        RedisModule,
        TypeOrmModule.forFeature([ShortEntity]),
      ],
      controllers: [ShortController],
      providers: [ShortService],
    }).compile();
    shortController = module.get<ShortController>(ShortController);
    shortService = module.get<ShortService>(ShortService);
    redisShortService = module.get<RedisShortService>(RedisShortService);
  });

  it('should be defined', () => {
    expect(shortController).toBeDefined();
  });

  describe('upload', () => {
    it('it should return sucess', async () => {
      let response = await shortController.upload({
        url: 'http://www.baidu.com',
      });
      expect(response).toEqual(
        expect.objectContaining({
          url: expect.any(String),
        }),
      );
      let code = new URL(response.url).pathname.split('/')[3];
      response = await shortController.get({ code });
      expect(response).toEqual(
        expect.objectContaining({
          url: expect.any(String),
        }),
      );
    });

    describe('getUrlByCode', () => {
      it('it should return sucess', async () => {
        try {
          await shortService.getUrlByCode(randomShortCode());
        } catch (error) {
          expect(error).toBeInstanceOf(HttpException);
        }
      });
    });

    describe('ShortService', () => {
      it('it should return sucess', async () => {
        config.short.alphabet = 'a';
        config.short.len = 1;
        await redisShortService.set(
          config.short.alphabet,
          config.short.alphabet,
        );
        try {
          await shortService.generateShortCode();
        } catch (error) {
          expect(error).toEqual('生成失败');
        }
      });
    });
  });
});
