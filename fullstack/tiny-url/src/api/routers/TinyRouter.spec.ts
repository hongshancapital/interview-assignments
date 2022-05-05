import supertest from 'supertest';
import ApiAppinstance from '../ApiApp';
import MongdbInstance from '../services/db/Mongoose';
import RedisInstance from '../services/db/RedisCache';

describe('Test Tinyurl Route', () => {
    const app = ApiAppinstance.getApplication();
    let request: supertest.SuperTest<supertest.Test>;

    beforeEach(() => {
        MongdbInstance.connect();
        request = supertest(app);
    });
    afterAll(() => {
        MongdbInstance.disconnect();
        RedisInstance.disconnect();
    });
    it('GET /api/:shortId', async () => {
        const response = await request.get('/api/123');
        expect(response.statusCode).toBe(400);
        expect(response.headers['content-type']).toEqual(expect.stringContaining('json'));
        expect(response.body.message).toBe('shortId is invalid');
        //expect(response.body).toMatchObject({message: 'shortId is invalid'});
    });
    it('POST /api/', async () => {
        const body = {url: ''};
        const response = await request.post('/api/').send(body);
        expect(response.statusCode).toBe(400);
        expect(response.headers['content-type']).toEqual(expect.stringContaining('json'));
        expect(response.body.message).toBe('url is not provided');
    });
});
