import chai from 'chai';
import chaiHttp from 'chai-http';

import init from './src';

chai.use(chaiHttp);
chai.should();

let app;

describe("TESTS", () => {
    before(async () => {
        app = await init();
    });

    describe("POST /shorter", () => {
        //Test to get all posts
        it("Should save shorter", (done) => {
            chai.request(app)
                .post('/shorter', { url: 'https://eten.wang/blogs' })
                .end((err, response) => {
                    response.should.have.status(200);
                    response.body.should.be.a('object');
                    done();
                });
        });
    });

    describe("GET /shorter/:shorter", () => {
        //Test to get all posts
        it("Should get url by shorter", (done) => {
            chai.request(app)
                .get('/shorter/rN6Uu1')
                .end((err, response) => {
                    response.should.have.status(200);
                    response.body.should.be.a('object');
                    done();
                });
        });
    });
});