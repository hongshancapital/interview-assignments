import supertest from 'supertest';
import ApiAppinstance from '../ApiApp';
import Urlmodel from '../models/UrlModel';
import MongdbInstance from '../services/db/Mongoose';
import Mysqlinstance from '../services/db/Sequelize';
import RedisInstance from '../services/db/RedisCache';

const app = ApiAppinstance.getApplication();
let request: supertest.SuperTest<supertest.Test>;
beforeAll(() => {
    Mysqlinstance.connect();
    MongdbInstance.connect();
    MongdbInstance.deleteMany();
    request = supertest(app);
});
afterAll(() => {
    jest.resetAllMocks();
    Mysqlinstance.close();
    MongdbInstance.disconnect();
    RedisInstance.disconnect();
});

afterEach(() => {
    jest.resetAllMocks();
    jest.restoreAllMocks();
    RedisInstance.flushall();
});
describe('Test Tinyurl Control:Tinyurl', () => {
    it('POST /api/, url is not provided', async () => {
        const body = {url: ''};

        const response = await request.post('/api/').send(body);

        expect(response.statusCode).toBe(400);
        expect(response.headers['content-type']).toEqual(expect.stringContaining('json'));
        expect(response.body.message).toBe('url is not provided');
    });
    it('POST /api/, url is invalid', async () => {
        const body = {url: 'http://wwwddaddd'};

        const response = await request.post('/api/').send(body);

        expect(response.statusCode).toBe(400);
        expect(response.headers['content-type']).toEqual(expect.stringContaining('json'));
        expect(response.body.message).toBe('url is invalid');
    });
    it('POST /api/, url has been exist', async () => {
        const ourl = 'http://www.google.om/1';
        const body = {url: ourl};
        const shortId = 'test';
        const Record = new Urlmodel({url: ourl, shortId: 'test'});

        const spy = jest.spyOn(MongdbInstance, 'getUrl').mockResolvedValue(Record);

        const response = await request.post('/api/').send(body);

        expect(spy).toBeCalledTimes(1);
        expect(response.statusCode).toBe(200);
        expect(response.headers['content-type']).toEqual(expect.stringContaining('json'));
        expect(response.body.url).toBe(ourl);
        expect(response.body.shortId).toBe(shortId);
    });
    it('POST /api/, create url successful', async () => {
        const ourl = 'http://www.google.om/11';
        const body = {url: ourl};

        const response = await request.post('/api/').send(body);

        expect(response.statusCode).toBe(200);
        expect(response.headers['content-type']).toEqual(expect.stringContaining('json'));
        expect(response.body.url).toBe(ourl);
        expect(response.body.shortId).toBeDefined;
    });
    it('POST /api/, The length of Tiny url exceed 8 bits', async () => {
        const ourl = 'http://www.google.om/11111';
        const body = {url: ourl};

        const spy = jest.spyOn(Mysqlinstance, 'dequeue').mockResolvedValue(68719476736n);

        const response = await request.post('/api/').send(body);

        expect(spy).toBeCalledTimes(1);
        expect(response.statusCode).toBe(500);
        expect(response.headers['content-type']).toEqual(expect.stringContaining('json'));
        expect(response.body).toMatchObject({message: 'The length of Tiny url exceed 8 bits'});
    });
});

describe('Test Tinyurl Control:GetTinyUrl', () => {
    it('GET /api/:shortId, not exist', async () => {
        const shortId = '123';
        const response = await request.get('/api/' + shortId);

        expect(response.statusCode).toBe(400);
        expect(response.headers['content-type']).toEqual(expect.stringContaining('json'));
        expect(response.body).toMatchObject({message: 'shortId is invalid'});
    });
    it('GET /api/:shortId, get from redis', async () => {
        const shortId = '3L';
        const url = 'http://www.google.com/redis';

        const spy = jest.spyOn(RedisInstance, 'get').mockResolvedValue(url);

        const response = await request.get('/api/' + shortId);

        expect(spy).toBeCalledTimes(1);
        expect(response.statusCode).toBe(200);
        expect(response.headers['content-type']).toEqual(expect.stringContaining('json'));
        expect(response.body).toMatch(url);
    });
    it('GET /api/:shortId, get from mongodb(not exit redis) & store in reids', async () => {
        const shortId = 'test';
        const url = 'http://www.google.com/mongo';
        const Record = new Urlmodel({url: url, shortId: shortId});

        const spy = jest.spyOn(MongdbInstance, 'getUrl').mockResolvedValue(Record);
        const spy1 = jest.spyOn(RedisInstance, 'set');

        const response = await request.get('/api/' + shortId);

        expect(spy).toBeCalledTimes(1);
        expect(spy1).toBeCalledWith(shortId, url);
        expect(response.statusCode).toBe(200);
        expect(response.headers['content-type']).toEqual(expect.stringContaining('json'));
        expect(response.body).toMatch(url);
    });
});
