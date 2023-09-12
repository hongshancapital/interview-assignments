import request from 'supertest';
import server from '../../src/app';
import * as shorten from '../../src/api/shorten';
import * as unshorten from '../../src/api/unshorten';

describe('Unit tests of <api>', () => {
    afterAll(() => {
        server.close();
    });
    test('get /favicon.ico', async () => {
        await request(server).get('/favicon.ico').send().expect(204);
    });

    describe('test /:code', () => {
        let spyUnshorten: jest.SpyInstance;
        beforeAll(() => {
            spyUnshorten = jest.spyOn(unshorten, 'default');
        });
        afterAll(() => {
            spyUnshorten.mockRestore();
        });
        beforeEach(() => {
            spyUnshorten.mockClear();
        });
        test('when unshorten success', async () => {
            spyUnshorten.mockResolvedValue('something');
            let res = await request(server).get('/somekey1');
            expect(res.statusCode).toBe(200);
            expect(res.body).toHaveProperty('url', 'something');
            expect(spyUnshorten).toBeCalledTimes(1);
        });
        test('when unshorten failed', async () => {
            spyUnshorten.mockRejectedValue({'status': 400, 'msg': 'wrong'});
            let res = await request(server).get('/somekey1');
            expect(res.statusCode).toBe(400);
            expect(res.body).toHaveProperty('status', 400);
            expect(res.body).toHaveProperty('msg', 'wrong');
            expect(spyUnshorten).toBeCalledTimes(1);
        });
    });

    describe('post /shorten', () => {
        let spyShorten: jest.SpyInstance;
        beforeAll(() => {
            spyShorten = jest.spyOn(shorten, 'default');
        });
        afterAll(() => {
            spyShorten.mockRestore();
        });
        beforeEach(() => {
            spyShorten.mockClear();
        });
        test('when shorten success', async () => {
            spyShorten.mockResolvedValue('somekey1');
            let res = await request(server).post('/shorten').send({'url': 'something'});
            expect(res.statusCode).toBe(200);
            expect(res.body).toHaveProperty('code', 'somekey1');
            expect(spyShorten).toBeCalledTimes(1);
        });
        test('when shorten failed', async () => {
            spyShorten.mockRejectedValue({'status': 400, 'msg': 'wrong'});
            let res = await request(server).post('/shorten').send({'url': 'something'});
            expect(res.statusCode).toBe(400);
            expect(res.body).toHaveProperty('status', 400);
            expect(res.body).toHaveProperty('msg', 'wrong');
            expect(spyShorten).toBeCalledTimes(1);
        });
    });
});
