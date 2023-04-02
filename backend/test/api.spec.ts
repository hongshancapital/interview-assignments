import { insertDomainNameInMySql, pool, updateShortCodeInMySql } from '../model/mysql';
import { app, server } from '../src/app';
import { redis } from '../model/redis';
import { generateShortCode } from '../service/codeUtil';
const request = require('supertest');

describe('app service', () => {
    beforeAll(async () => {
        await pool.query('TRUNCATE TABLE domainNames');
    });
    afterAll(async () => {
        await pool.end();
        await redis.flushdb();
        await redis.quit();
        server.close();
    });

    describe('Put /longDomainName', () => {
        it('should return a short code for a valid long domain name', async () => {
            const payload = { longDomainName: 'example.com' };
            const response = await request(app).put('/longDomainName').send(payload);

            expect(response.status).toBe(200);
            expect(response.text).toMatch(/^[a-zA-Z0-9]+$/);
        });

        it('should return status 400 for an invalid long domain name ', async () => {
            const payload = { longDomainName: 'example_.com' };
            const response = await request(app).put('/longDomainName').send(payload);

            expect(response.status).toBe(400);
        });

        it('should return status 409 for an exist long domain name ', async () => {
            const payload = { longDomainName: 'example.com' };
            const response = await request(app).put('/longDomainName').send(payload);

            expect(response.status).toBe(409);
        });
    });

    describe('GET /longDomainName', () => {
        it('should return the cached domain name for a valid short code', async () => {
            const { text: code } = await request(app).put('/longDomainName').send({
                longDomainName: '1.example.com',
            });
            const response = await request(app).get(`/longDomainName/${code}`);

            expect(response.status).toBe(200);
            expect(response.text).toBe('1.example.com');
        });

        it('should return the domain name for a valid short code which is not in cache', async () => {
            const id = await insertDomainNameInMySql('2.example.com');
            const code = generateShortCode(id);
            await updateShortCodeInMySql(id, code);
            const response = await request(app).get(`/longDomainName/${code}`);
            expect(response.status).toBe(200);
            expect(response.text).toBe('2.example.com');
        });

        it('should return status = 400 for an invalid short code ', async () => {
            const response = await request(app).get('/longDomainName/invalid-short-code');

            expect(response.status).toBe(400);
        });
        it('should return status = 408 for an uncreated short code ', async () => {
            const uncreatedShortCode = generateShortCode(4);
            const response = await request(app).get('/longDomainName/' + uncreatedShortCode);
            expect(response.status).toBe(408);
        });
    });

});