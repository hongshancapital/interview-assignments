import request from 'supertest';
import { readFileSync } from 'fs';
import { memcached } from '../../src/database/memcached';
import { writePool, readPool } from '../../src/database/mysql';
import server from '../../src/app';
import logger from '../../src/log/log';

describe('Integration test', () => {
    async function memcachedFlush(memcached): Promise<void> {
        return new Promise<void>((resolve, reject) => {
            memcached.flush((err, results) => {
                if (err) {
                    logger.debug(`Memcached flush failed.`, err);
                    reject(err);
                } else {
                    resolve();
                }
            });
        });
    }
    beforeAll(async () => {
        await memcachedFlush(memcached).then(() => {
            logger.debug('Memcached flushed.');
        }).catch((err) => {
            logger.debug(`Memcached flush failed.`, err);
        });
        const sql = readFileSync(__dirname + '/test.sql');
        await writePool.execute('truncate table short_url')
        .then(() => {
            writePool.execute(sql.toString())
        })
        .then(() => {
            logger.debug('Mysql table inited.');
        }).catch((err) => {
            logger.debug(`Mysql table init failed.`, err);
        });
    });
    afterAll(async () => {
        await writePool.end();
        await readPool.end();
        memcached.end();
        server.close();
    });
    test('get /favicon.ico', async () => {
        await request(server).get('/favicon.ico').send().expect(204);
    });

    describe('post /shorten', () => {
        test('when input is empty.', async () => {
            let res = await request(server).post('/shorten').send({ 'url': '' });
            expect(res.statusCode).toBe(400);
            expect(res.body).toHaveProperty('status', 400);
            expect(res.body).toHaveProperty('msg', 'Please input a url.');
        });
        test('when input is too long.', async () => {
            const input: string = 'a'.repeat(2049);
            let res = await request(server).post('/shorten').send({ 'url': input });
            expect(res.statusCode).toBe(400);
            expect(res.body).toHaveProperty('status', 400);
            expect(res.body).toHaveProperty('msg', `Url exceeds max size 2048.`);
        });
        test('when input is not a valid url.', async () => {
            let res = await request(server).post('/shorten').send({ 'url': 'something' });
            expect(res.statusCode).toBe(400);
            expect(res.body).toHaveProperty('status', 400);
            expect(res.body).toHaveProperty('msg', `Please input a valid url.`);
        });
        test('when input is a valid url.', async () => {
            let res = await request(server).post('/shorten').send({ 'url': 'https://baidu.com' });
            expect(res.statusCode).toBe(200);
            expect(res.body).toHaveProperty('code', 'u24ILVw2');
        });
        test('when input is a existed url.', async () => {
            let res = await request(server).post('/shorten').send({ 'url': 'https://baidu.com' });
            expect(res.statusCode).toBe(200);
            expect(res.body).toHaveProperty('code', 'u24ILVw2');
        });
        test('when first code is coccupied, pick first unused.', async () => {
            let res = await request(server).post('/shorten').send({ 'url': 'https://hello.com' });
            expect(res.statusCode).toBe(200);
            expect(res.body).toHaveProperty('code', 'oZpUFIKZ');
        });
        test('when first code is coccupied, another code matched.', async () => {
            let res = await request(server).post('/shorten').send({ 'url': 'https://google.com' });
            expect(res.statusCode).toBe(200);
            expect(res.body).toHaveProperty('code', 'ZmevP23j');
        });
        test('when first code is coccupied, another code matched.', async () => {
            let res = await request(server).post('/shorten').send({ 'url': 'https://google.com' });
            expect(res.statusCode).toBe(200);
            expect(res.body).toHaveProperty('code', 'ZmevP23j');
        });
        test('when all codes are coccupied, pick the eldest.', async () => {
            let res = await request(server).post('/shorten').send({ 'url': 'https://world.com' });
            expect(res.statusCode).toBe(200);
            expect(res.body).toHaveProperty('code', 'ERIw1KuF');
        });
    });

    describe('test /:code', () => {
        test('when input is not a valid short url.', async () => {
            let res = await request(server).get('/somekey');
            expect(res.statusCode).toBe(400);
            expect(res.body).toHaveProperty('msg', 'Please input a valid short url.');
        });
        test('when input is not a valid short url.', async () => {
            let res = await request(server).get('/somekey+');
            expect(res.statusCode).toBe(400);
            expect(res.body).toHaveProperty('msg', 'Please input a valid short url.');
        });
        test('when code is not correct.', async () => {
            let res = await request(server).get('/somekey1');
            expect(res.statusCode).toBe(404);
            expect(res.body).toHaveProperty('status', 404);
            expect(res.body).toHaveProperty('msg', 'Short url: somekey1 is not found.');
        });
        test('when code is correct.', async () => {
            let res = await request(server).get('/u24ILVw2');
            expect(res.statusCode).toBe(200);
            expect(res.body).toHaveProperty('url', 'https://baidu.com');
        });
    });

});
