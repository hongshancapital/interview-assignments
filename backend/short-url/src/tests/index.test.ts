import request from 'supertest';
import App from 'app';
import IndexRoute from '@routes/index.route';
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

    describe('[GET] /', () => {
        it('response ok', async () => {
            const indexRoute = new IndexRoute();

            const app = new App([indexRoute]);
            return request(app.getServer()).get(indexRoute.path).expect(200);
        });
    });

    describe('[GET] /unknown ', () => {
        it('response 404 not found', async () => {
            const indexRoute = new IndexRoute();

            try {
                const app = new App([indexRoute]);
                await request(app.getServer()).get(`${indexRoute.path}/abc`);
            } catch (error) {
                expect(error.status).toEqual(404);
            }
        });
    });
});
