import { describe, it } from 'mocha';
import assert from 'assert';
import { reqOfGet, getDataBylongName, clearDomain, createDomainName } from './testUtil';

describe('test api', function () {
    const testLongDomainName = 'https://blog.csdn.net/qq_41480099/article/details/81873468';
    const testLongDomainNameNotStandard = 'wetyriweoruowe';
    const testShortName = 'Q1Q9sk';
    const tempUrl = `https://127.0.0.1:3000/api/v1/tinyName`;
    describe('test get short domain name api', function () {
        afterEach(function () {
            clearDomain();
        });
        it('longName of arg is not undefined and standard,it is ok', async function () {
            const res = await reqOfGet(`/domainName/short`, { longName: testLongDomainName });
            const shortName = await getDataBylongName(testLongDomainName);
            assert.deepEqual(res, {
                code: 200,
                message: '获取短域名成功',
                data: `${tempUrl}/${shortName[0][0].short_name}`
            });
        });
        it('longName of arg is undefined,it is error', async function () {
            const res = await reqOfGet('/domainName/short');
            assert.deepEqual(Object.keys(res), ['code', 'message']);
            assert.equal(res.code, 400);
            assert.equal(res.message, '没有传递longName参数');
        });
        it('longName of arg is not undefined but not standard,it is error', async function () {
            const res = await reqOfGet(`/domainName/short`, { longName: testLongDomainNameNotStandard });
            assert.deepEqual(Object.keys(res), ['code', 'message']);
            assert.equal(res.code, 400);
            assert.equal(res.message, '传递参数不符合url标准');
        });
        it('long domain name is already exist,it is ok', async function () {
            await createDomainName(testLongDomainName, testShortName);
            const res = await reqOfGet(`/domainName/short`, { longName: testLongDomainName });
            assert.deepEqual(res, {
                code: 200,
                message: '获取短域名成功',
                data: `${tempUrl}/${testShortName}`
            });
        });
    });
    describe('test get long domain name api', function () {
        afterEach(function () {
            clearDomain();
        });
        it('shortName of arg is undefined,it is error', async function () {
            const res = await reqOfGet('/domainName/long');
            assert.deepEqual(Object.keys(res), ['code', 'message']);
            assert.equal(res.code, 400);
            assert.equal(res.message, '没有传递shortName参数');
        });
        it('shortName of arg is exist,it is error', async function () {
            const res = await reqOfGet(`/domainName/long`, { shortName: testShortName });
            assert.deepEqual(Object.keys(res), ['code', 'message']);
            assert.equal(res.code, 400);
            assert.equal(res.message, '短域名无效，无法获取长域名');
        });
        it('shortName of arg is right,it is ok', async function () {
            await createDomainName(testLongDomainName, testShortName);
            const res = await reqOfGet(`/domainName/long`, { shortName: testShortName });
            assert.deepEqual(res, {
                code: 200,
                message: '获取长域名成功',
                data: testLongDomainName
            });
        });
    });
});