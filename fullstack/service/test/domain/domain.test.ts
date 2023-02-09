import { describe, it, before } from 'mocha';
import assert from 'assert';
import server from 'supertest';

import { Domains } from '@/models';
const service = server('http://localhost:3003');

describe('Unit Domain', () => {
    before(() => {
        Domains.removeMany({});
    });

    describe('#接收长域名信息，返回短域名信息', () => {
        it('缺少长域名', done => {
            service
                .post('/api/domainmanager/v1/domain')
                .send({})
                .expect('Content-Type', /json/)
                .expect(400, done)
                .expect(res => {
                    assert.strictEqual(res.body.code, 'INVALID_ARGUMENTS');
                });
        });

        it('长域名格式错误', done => {
            service
                .post('/api/domainmanager/v1/domain')
                .send({ url: '6de89414-ae3e-4570-a6a8-958cc3bce434' })
                .expect('Content-Type', /json/)
                .expect(400, done)
                .expect(res => {
                    assert.strictEqual(res.body.code, 'INVALID_ARGUMENTS');
                });
        });

        const url = 'https://github.com/scdt-china/interview-assignments/tree/master/fullstack/ss';

        it('长域名参数正常', done => {
            service
                .post('/api/domainmanager/v1/domain')
                .send({ url })
                .expect('Content-Type', /json/)
                .expect(200, done)
                .expect(res => {
                    assert.notDeepStrictEqual(res.body, {
                        url,
                        compressed: 'xxxxx',
                        type: 'xxxxx'
                    });
                });
        });
    });

    describe('#接收短域名信息，返回长域名信息', () => {
        it('缺少短域名参数', done => {
            service
                .get('/api/domainmanager/v1/domain/original')
                .query({})
                .expect('Content-Type', /json/)
                .expect(400, done)
                .expect(res => {
                    assert.strictEqual(res.body.code, 'INVALID_ARGUMENTS');
                });
        });
        it('短域名格式不正确', done => {
            service
                .get('/api/domainmanager/v1/domain/original')
                .query({ shortUrl: 'xxxxx123' })
                .expect('Content-Type', /json/)
                .expect(400, done)
                .expect(res => {
                    assert.strictEqual(res.body.code, 'INVALID_ARGUMENTS');
                });
        });
        it('短域名参数不合法', done => {
            service
                .get('/api/domainmanager/v1/domain/original')
                .query({ shortUrl: 'https://github.com/2PSwE7a5ds' })
                .expect('Content-Type', /json/)
                .expect(403, done)
                .expect(res => {
                    assert.strictEqual(res.body.code, 'FORBIDDEN');
                });
        });
        it('正常短域名', done => {
            service
                .get('/api/domainmanager/v1/domain/original')
                .query({ shortUrl: 'https://github.com/2PSwE7' })
                .expect('Content-Type', /json/)
                .expect(200, done)
                .expect(res => {
                    assert.notDeepStrictEqual(res.body, {
                        url: ''
                    });
                });
        });
    });
});
