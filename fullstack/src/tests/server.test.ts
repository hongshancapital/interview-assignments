import chai from 'chai';
import chaiHttp from 'chai-http';
import Server from '../server';
import {GetLongNameFromRedis} from "../utils/redis_setup";

chai.use(chaiHttp);
chai.should();

describe("server file test", () => {
    describe("store shorter domain name by long domain name", () => {
        it("should get -1 wrong long name", (done) => {
            chai.request(Server).post("/long-to-short")
                .set({'Content-Type': 'application/json'})
                .send({longDomain: 'baidu'})
                .end((err, res) => {
                    res.should.have.status(400);
                });
            done();
        });

        it("should get -1 wrong long name", (done) => {
            chai.request(Server).post("/long-to-short")
                .end((err, res) => {
                    res.should.have.status(400);
                });
            done();
        });

        it("should store long and short map", (done) => {
            chai.request(Server).post("/long-to-short")
                .set({'Content-Type': 'application/json'})
                .send({longDomain: 'www.baidu.com'})
                .end((err, res) => {
                    res.should.have.status(200);
                    const shorter = res.body.shortDomain;
                    shorter.length.should.equal(8);
                });
            done();
        });
    });

    describe("get long domain name by short name", () => {
        it("should get -2 bad request", (done) => {
            chai.request(Server).get('/short-to-long')
                .end((err, res) => {
                    res.should.have.status(400);
                });
            done();
        });

        it("should get -3 wrong shortDomain", (done) => {
            chai.request(Server).get('/short-to-long?query=' + JSON.stringify({ss: "asdfasdf"}))
                .end((err, res) => {
                    res.should.have.status(400);
                });

            chai.request(Server).get('/short-to-long?query=' + JSON.stringify({shortDomain: "asdfas"}))
                .end((err, res) => {
                    res.should.have.status(400);
                });

            done();

        });

        it("should get -4 short invalid error", (done) => {
            chai.request(Server).get('/short-to-long?query=' + JSON.stringify({shortDomain: "x.xx/DDD"}))
                .end((err, res) => {
                    res.should.have.status(400);
                });
            done();
        });

        it('should get longname', async () => {
            const shorter = await GetLongNameFromRedis("www.google.com");

            chai.request(Server).get('/short-to-long?query=' + JSON.stringify({shortDomain: shorter}))
                .end((err, res) => {
                    res.should.have.status(200);
                });
        });
    });
});