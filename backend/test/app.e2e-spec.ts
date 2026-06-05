import request from 'supertest';
import Mongo from '@/db/mongodb';
import server from '@/app';

describe('v1 (e2e)', () => {
    const app = server;

    beforeAll(async () => {
        await Mongo.connect();
        app.listen(parseInt(process.env.PORT || '3000'));
    });

    afterAll(async () => {
        await Mongo.server.dropDatabase();
        await Mongo.close();
    });

    let testDomain: null | { compressed: string } = null;

    it('/api/v1/domain (POST)', () => {
        return request(app)
            .post('/api/v1/domain')
            .send({ url: 'https://www.expressjs.com.cn/starter/hello-world.html', name: 'express' })
            .expect(200)
            .expect(res => {
                testDomain = res.body.data;
            });
    });

    it('/api/v1/domain (GET)', () => {
        return request(app)
            .get('/api/v1/domain')
            .query({ shortUrl: testDomain?.compressed })
            .expect(200);
    });
});
