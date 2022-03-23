import { Test, TestingModule } from '@nestjs/testing';
import { TypeOrmModule } from '@nestjs/typeorm';
import { DatabaseConfigModule } from '../modules/config/database.module';
import { ShorturlEntity } from './shorturl.entity';
import { ShorturlService } from './shorturl.service';

describe('ShorturlController', () => {
  let shorturlService: ShorturlService;

  beforeEach(async () => {
    const app: TestingModule = await Test.createTestingModule({
      imports: [
        DatabaseConfigModule,
        TypeOrmModule.forFeature([ShorturlEntity])
      ],
      providers: [ShorturlService],
    }).compile();

    shorturlService = app.get<ShorturlService>(ShorturlService);
  });

  it('Service should return url of the shorturl', async () => {
    const s_url = '73F5DE09'
    const url = 'http://localhost:3332/api/swagger/'
    const res = await shorturlService.findOneBy({
      s_url
    });
    expect(res.url).toEqual(url)
  });

  // it('Service should return url not of the shorturl', async () => {
  //   const s_url = 'XXF5DE09'
  //   const url = 'http://localhost:3332/api/swagger/'
  //   const res = await shorturlService.findOneBy({
  //     s_url
  //   });
  //   expect(res).toEqual(null)
  // });
});
