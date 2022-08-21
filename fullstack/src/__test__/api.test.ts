import { server } from '../src/web';
import supertest from 'supertest';

const api = supertest(server);

describe('Web api test', () => {
    it('/ should return welcome', async () => {
        const response = await api.get('/')
            .expect(200)
            .expect('welcome')
    })
    it('/favicon.ico should return 404', async () => {
        const response = await api.get('/favicon.ico')
            .expect(404)
    })
    it('get /create should return 405', async () => {
        const response = await api.get('/create')
            .expect(405)
    })
    it('post /create with invalid json', async () => {
        const response = await api.post('/create').send("")
            .expect(500)
            .expect('invalid json')
    })
    it('post /create with long url', async () => {
        const response = await api.post('/create').send({ "url": "http://0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345" })
            .expect(500)
            .expect('url too long')
    })
    it('post /create with invalid url', async () => {
        const response = await api.post('/create').send({ "url": "abc" })
            .expect(500)
            .expect('invalid url')
    })

    it('get /not_exists', async () => {
        const response = await api.get('/not_exists')
            .expect(500)
            .expect('url not found')
    })

    it('post /create', async () => {
        const response = await api.post('/create').send({ "url": "http://www.baidu.com" })
            .expect(200)
            .expect('3kb2uL')
    })

    it('get /3kb2uL', async () => {
        const response = await api.get('/3kb2uL').expect(301)
        expect(response.header).toHaveProperty('location', 'http://www.baidu.com')
    })
})

describe('hash confilct test', () => {

    const OLD_ENV = process.env;

    beforeEach(() => {
        jest.resetModules()
        process.env = { ...OLD_ENV };
    });

    afterAll(() => {
        process.env = OLD_ENV;
    });

    it('get /3kb2uL should return url not found', async () => {
        process.env.BF_SIZE = '10';
        process.env.BF_FPP = '0.9';
        let server = require('../src/web').server;
        let api = supertest(server);
        let si = require('../src/store').storeInstance;

        expect(si.has('3kb2uL')).toBe(false);
        for (let i = 0; i < 100; i++) {
            si.save(i.toString(), i.toString());
        }
        expect(si.has('3kb2uL')).toBe(true);
        const response = await api.get('/3kb2uL')
            .expect(500)
            .expect('url not found')
    })
})