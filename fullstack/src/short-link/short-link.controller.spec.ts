import { Test, TestingModule } from '@nestjs/testing';
import { ShortLinkController } from './short-link.controller';
import { ShortLinkService } from './short-link.service';
import { LinkEntity } from '../entities/link.entity';
import { TypeOrmModule } from '@nestjs/typeorm';
import { IdModule } from '../id/id.module';
import { RedisModule } from '@liaoliaots/nestjs-redis';
import { getRedisConfig, getDbConfig } from '../config';
import * as URL from 'url';

describe('ShortLinkController', () => {
  let controller: ShortLinkController;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      imports: [
        TypeOrmModule.forFeature([LinkEntity]),
        TypeOrmModule.forRoot(getDbConfig()),
        RedisModule.forRoot(getRedisConfig()),
        IdModule,
      ],
      providers: [ShortLinkService],
      controllers: [ShortLinkController],
    }).compile();

    controller = module.get<ShortLinkController>(ShortLinkController);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });

  // 查询不存在的链接，返回404，查询参数错误时，返回400
  it('should not find link', async () => {
    const params = { id: '^3$654' };
    try {
      const linkEntity = await controller.findLink(params);
      expect(linkEntity === null).toBeTruthy();
    } catch (error) {
      expect(error.status === 404).toBeTruthy();
    }

    const errorParams = { id: { id: '32424' } };
    try {
      const linkEntity = await controller.findLink({ ...errorParams });
      expect(linkEntity === null).toBeTruthy();
    } catch (error) {
      expect(error.status === 400).toBeTruthy();
    }
  });

  // 创建链接id成功后，获取链接与提交链接相同
  it('should create link success', async () => {
    const link = 'https://www.baidu.com';
    const protocol = 'http';
    const headers = {
      host: 'localhost:3000',
    };
    try {
      const { statusCode } = await controller.createLink(
        { link },
        { protocol, headers },
      );
      expect(statusCode === 201).toBeTruthy();
    } catch (error) {
      console.log('should create link success', error);
      expect(error === null).toBeTruthy();
    }
  });

  // 创建链接参数格式有问题时，返回400
  it('should create link failed, statuscode :400', async () => {
    const link = { url: 'http://www.baidu.com' };
    const protocol = 'http';
    const headers = {
      host: 'localhost:3000',
    };
    try {
      const { statusCode } = await controller.createLink(
        { link },
        { protocol, headers },
      );
      expect(statusCode === 400).toBeTruthy();
    } catch (error) {
      console.log('should create link failed, statuscode :400', error);
      expect(error === null).not.toBeTruthy();
    }
  });

  // 创建链接参数非正常链接，返回400
  it('should create link failed, statuscode :400', async () => {
    const link = 'mail://www.baidu.com';
    const protocol = 'http';
    const headers = {
      host: 'localhost:3000',
    };
    try {
      const { statusCode } = await controller.createLink(
        { link },
        { protocol, headers },
      );
      expect(statusCode === 400).toBeTruthy();
    } catch (error) {
      console.log(error);
      expect(error === null).not.toBeTruthy();
    }
  });

  // 先创建，在查找，正常返回302
  it('should find link success', async () => {
    const link = 'https://www.baidu.com';
    const protocol = 'http';
    const headers = {
      host: 'localhost:3000',
    };
    try {
      const { statusCode, link: createLink } = await controller.createLink(
        { link },
        { protocol, headers },
      );
      expect(statusCode === 201).toBeTruthy();
      const pathname = URL.parse(createLink).pathname.replace('/', '');
      expect(pathname.length).toBeLessThanOrEqual(8);
      const { url, statusCode: findRspStatusCode } = await controller.findLink({
        id: pathname,
      });
      expect(url === link).toBeTruthy();
      expect(findRspStatusCode === 302).toBeTruthy();
    } catch (error) {
      console.log('should find link success', error);
      expect(error === null).toBeTruthy();
    }
  });
});
