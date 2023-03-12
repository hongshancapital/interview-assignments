import request from 'supertest';
import App from 'app';
import { CreateShortUrlDto } from '@dtos/short-url.dto';
import ShortsRoute from '@routes/short-url.route';
import { closeDB, loadDB } from '@utils/database';
import { closeRedis } from '@utils/redis';

describe('Testing Shorts', () => {
    beforeAll(async () => {
        await loadDB();
    });

    afterAll(async () => {
        setTimeout(closeDB, 1000);
        await closeRedis();
    });

    describe('[GET] /shorts/:shortKey existed', () => {
        it('response findOne short', async () => {
            const shortKey = '0NIzgWGX';

            const shortsRoute = new ShortsRoute();

            const app = new App([shortsRoute]);
            return request(app.getServer()).get(`${shortsRoute.path}/${shortKey}`).expect(200);
        });
    });

    describe('[GET] /shorts/:shortKey not found', () => {
        it('response findOne short', async () => {
            const shortKey = 'nullUrl';

            const shortsRoute = new ShortsRoute();

            try {
                const app = new App([shortsRoute]);
                await request(app.getServer()).get(`${shortsRoute.path}/${shortKey}`);
            } catch (error) {
                expect(error.status).toEqual(404);
            }
        });
    });

    describe('[POST] /shorts create', () => {
        it('response Create short', async () => {
            const mockLongUrl = 'https://www.google.com';
            const randomUrl = `${mockLongUrl}?r=${Math.floor(Math.random() * 1000)}`;

            const shortData: CreateShortUrlDto = {
                longUrl: randomUrl
            };

            const shortsRoute = new ShortsRoute();

            const app = new App([shortsRoute]);
            return request(app.getServer()).post(`${shortsRoute.path}`).send(shortData).expect(201);
        });
    });
});
