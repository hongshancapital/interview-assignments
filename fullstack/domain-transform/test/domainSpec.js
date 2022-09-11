var request = require('supertest');
var express = require('express');
var app = new express();
var port = 3000;
app.set('port', port);
describe('域名转换单元测试', function() {

    it('根据短域名返回长域名-正常返回', function(done) {
        request(app)
            .get('/domain/queryDomain/1111.com')
            .set('Cache-control', 'no-cache')
            .expect(200, {
                "code": "200",
                "msg": "查找成功",
                "data": [
                    {
                        "longDomain": "www.baidufanyi1111.com"
                    }
                ]
            });
            done();
    });

    it('根据短域名返回长域名-域名格式错误', function(done) {
        request(app)
            .get('/domain/queryDomain/1111')
            .set('Cache-control', 'no-cache')
            .expect(200, {
                "code": "1",
                "msg": "域名格式错误",
                "data": "域名格式错误"
            });
            done();
    });

    it('根据短域名返回长域名-域名长度超出', function(done) {
        request(app)
            .get('/domain/queryDomain/11111.com')
            .set('Cache-control', 'no-cache')
            .expect(200, {
                "code": "1",
                "msg": "短域名长度超出限制，最多为8个字符",
                "data": "短域名长度超出限制，最多为8个字符"
            });
            done();
    });

    it('根据长域名信息返回短域名信息-正常存储', function(done) {
        request(app)
            .post('/domain/saveDomain')
            .expect('Content-Type', 'text/html; charset=utf-8')
            .send({
                "longDomain": "www.baidufanyi.com",
                "domainDescription": "测试域名"
            })
            .expect('Content-Type', 'text/html; charset=utf-8')
            .expect(200,
                {
                    "code": "200",
                    "msg": "存储成功",
                    "data": {
                        "shortDomain": "anyi.com"
                    }
                });
            done();
    });

    it('根据长域名信息返回短域名信息-域名格式错误', function(done) {
        request(app)
            .post('/domain/saveDomain')
            .expect('Content-Type', 'text/html; charset=utf-8')
            .send({
                "longDomain": "baidufany",
                "domainDescription": "测试域名"
            })
            .expect('Content-Type', 'text/html; charset=utf-8')
            .expect(200,
                {
                    "code": "1",
                    "msg": "域名格式错误",
                    "data": "域名格式错误"
                });
            done();
    });

    it('根据长域名信息返回短域名信息-域名已存在', function(done) {
        request(app)
            .post('/domain/saveDomain')
            .expect('Content-Type', 'text/html; charset=utf-8')
            .send({
                "longDomain": "www.baidufanyi.com",
                "domainDescription": "测试域名"
            })
            .expect('Content-Type', 'text/html; charset=utf-8')
            .expect(200,
                {
                    "code": "1",
                    "msg": "该域名已存在",
                    "data": "该域名已存在"
                });
            done();
    });

});
