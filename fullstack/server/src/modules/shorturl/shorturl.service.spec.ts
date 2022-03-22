import { Test, TestingModule } from '@nestjs/testing';
import { TypeOrmModule } from '@nestjs/typeorm';
import { ShorturlEntity } from './shorturl.entity';
import { ShorturlService } from './shorturl.service';

describe('ShorturlController', () => {
  let shorturlService: ShorturlService;

  beforeEach(async () => {
    const app: TestingModule = await Test.createTestingModule({
      imports: [
        TypeOrmModule.forRoot(),
        TypeOrmModule.forFeature([ShorturlEntity])
      ],
      providers: [ShorturlService],
    }).compile();

    shorturlService = app.get<ShorturlService>(ShorturlService);
  });

  it('Service should return url of the shorturl', async () => {
    const s_url = '8C1AAA5F'
    const url = 'http://localhost:3332/api/swagger/'
    const res = await shorturlService.findOneBy({
      s_url
    });
    return expect(res.url).toEqual(url)
  });
});
