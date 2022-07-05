
import app from "../../app";
import request from "supertest";

import {
    StatusCodes,
} from 'http-status-codes';
describe('integration tests', () => {


    it('can get short url', async () => {
        const originUrl = 'https://github.com/scdt-china/interview-assignments/tree/master/fullstack';
        const result = await request(app)
            .post('/api/domain/shorten')
            .set('Accept', 'application/json')
            .send({
                url:originUrl
            })
            .expect(StatusCodes.OK);
        expect(result.text.length).toBeLessThan(originUrl.length);
    });
    it('can recover short url', async () => {
        const originUrl = 'https://www.google.com';
        let code = "";
        const result = await request(app)
            .post('/api/domain/shorten')
            .set('Accept', 'application/json')
            .send({
                url:originUrl
            })
            .expect((res) => {

                code = JSON.parse(res.text).short_url;
                request(app)
                    .post('/api/domain/shorten')
                    .set('Accept', 'application/json')
                    .send({
                        code
                    })
                    .expect((res1) => {

                        expect(JSON.parse(res1.text).origin_url).toEqual(originUrl);
                    })
                    .expect(StatusCodes.OK);
            })
            .expect(StatusCodes.OK);



    });

});