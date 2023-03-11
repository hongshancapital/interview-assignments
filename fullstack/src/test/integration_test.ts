import { assert } from "chai";
import request from "supertest";

import app from "app"
import * as service from 'services/shortener_service'

describe("integration test: get long url from short url", function() {
    it("api returns error with non existent short url", function (done){
        const urlPath = "/go/nonexist";
        request(app)
            .get(urlPath)
            .expect(400)
            .then(response => {
                assert.equal(
                    response?.body?.data, 
                    'Invalid short URL');
                done();
            });
    });

    it("api return valid long url", function (done){
        const longUrl = 'http://this-is-a-long-url.com';
        const shortUrlPostfix = 
            service.shortenUrl(longUrl).slice(-1*service.SHORT_URL_LENGTH);
        request(app)
            .get("/go/" + shortUrlPostfix)
            .expect(200)
            .then(response => {
                assert.equal(
                    response?.body?.data, 'http://this-is-a-long-url.com');
                done();
            });
    });

    it("api return error with invalid short url", function (done){        
        request(app)
            .get("/go/moreThanEightCharacters")
            .expect(400, done);
    });
    
});

describe("integration test: generate short url from long url", function() {
    it("api returns short url base case", function (done){
        const longUrl = 'http://this-is-a-long-url.com';
        request(app)
            .post("/shorten")
            .send('url=' + longUrl)
            .expect(200)
            .then(response => {
                assert.equal(response?.body?.data, 'http://go/00000001');
                done();
            });
    });

    it("api returns short url for long url already existed", function (done){
        const longUrl = 'http://this-is-a-long-url.com';
        const shortUrl = service.shortenUrl(longUrl);
        request(app)
            .post("/shorten")
            .send({url: longUrl})
            .set('Accept', 'application/json')
            .expect(200)
            .then(response => {
                assert.equal(response?.body?.data, shortUrl);
                done();
            });
    });

    it("api returns error for invalid url", function (done){
        request(app)
            .post("/shorten")
            .send('url=abcde')
            .expect(400, done)
    });

    it("api returns error for empty post data", function (done){
        request(app)
            .post("/shorten")
            .send({})
            .expect(400, done);
    });
});
