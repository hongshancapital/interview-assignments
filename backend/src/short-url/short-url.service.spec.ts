import { Test, TestingModule } from "@nestjs/testing";
import { ShortUrlService } from "./short-url.service";
import { TypeOrmModule } from "@nestjs/typeorm";
import { ShortUrlRepository } from "./entity/short-url.repository";
import { ShortUrlController } from "./short-url.controller";
import { StatusCode } from "./model/status_code";

describe('ShortUrlService', () => {
    let service: ShortUrlService;

    beforeEach(async () => {
        const module: TestingModule = await Test.createTestingModule({
            imports: [TypeOrmModule.forRoot({
                type: 'mysql',
                host: '127.0.0.1',
                username: 'root',
                password: 'taognrt!@#01',
                database: 'sequoia_demo',
                entities: [ShortUrlRepository],
                synchronize: true,
            }), TypeOrmModule.forFeature([ShortUrlRepository])],
            controllers: [ShortUrlController],
            providers: [ShortUrlService],
        }).compile();

        service = module.get<ShortUrlService>(ShortUrlService);
    });

    describe('createShortUrl', () => {
        it('should return Params is null if long url is null', async () => {
            const emptyLongUrl = '';
            const firstShortUrl = await service.createShortUrl(emptyLongUrl);
            expect(firstShortUrl.statusCode).toEqual(StatusCode.ParamsIsNull);
        });

        it('should return invalid long url if long url is valid format', async () => {
            const invalidLongUrl = 'fdsafasfm';
            const firstShortUrl = await service.createShortUrl(invalidLongUrl);
            expect(firstShortUrl.statusCode).toEqual(StatusCode.ParamsInvalid);
        });

        it('should return existing short url if long url already exists', async () => {
            const existingLongUrl = 'https://example.com';
            const firstShortUrl = await service.createShortUrl(existingLongUrl);
            const secondShortUrl = await service.createShortUrl(existingLongUrl);
            expect(firstShortUrl.result).toEqual(secondShortUrl.result);
        });

        it('should return new short url if long url does not exist', async () => {
            const nonExistingLongUrl = 'https://notexist.com';
            const firstShortUrl = await service.createShortUrl(nonExistingLongUrl);
            expect(firstShortUrl.result).not.toEqual(nonExistingLongUrl);
        });
    });

    describe('getLongUrl', () => {
        it('should return Params is null if short url is null', async () => {
            const emptyLongUrl = '';
            const firstShortUrl = await service.getLongUrl(emptyLongUrl);
            expect(firstShortUrl.statusCode).toEqual(StatusCode.ParamsIsNull);
        });

        it('should return invalid short url if short url is valid format', async () => {
            const invalidShortUrl = 'fdsafasfm';
            const firstShortUrl = await service.getLongUrl(invalidShortUrl);
            expect(firstShortUrl.statusCode).toEqual(StatusCode.ParamsInvalid);
        });

        it('should return long url if short url exists', async () => {
            const existingLongUrl = 'https://example.com';
            const shortUrl = await service.createShortUrl(existingLongUrl);
            const longUrl = await service.getLongUrl(shortUrl.result);
            expect(longUrl.result).toEqual(existingLongUrl);
        });

        it('should return "Not found" if short url does not exist', async () => {
            const nonExistingShortUrl = 'http://www.abcde.com';
            const longUrl = await service.getLongUrl(nonExistingShortUrl);
            expect(longUrl.statusCode).toEqual(StatusCode.NotFound);
        });
    });
});