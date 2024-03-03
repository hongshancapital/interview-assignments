import chai from 'chai';
import chaiHttp from "chai-http";
import Server, {BASE_URL, GENERATE_LENGTH, MAINFRAME_CODE} from "../../server";
import {ClearDb, PostgresClient, SaveMapToDb} from "../../utils/postgres_setup";
import {RedisClient} from "../../utils/redis_setup";

chai.use(chaiHttp);
chai.should();

describe("get long domain name by short name", () => {
    before(async () => {
        await ClearDb();
        await RedisClient.flushAll();
    });

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
        chai.request(Server).get('/short-to-long?query=' +
            JSON.stringify({shortDomain: BASE_URL + MAINFRAME_CODE + "DDDDDDD"}))
            .end((err, res) => {
                res.should.have.status(400);
            });
        done();
    });

    it('should get long domain by short from cache', (done) => {
        chai.request(Server).post('/long-to-short')
            .send({longDomain: "www.google.com"})
            .end((err, res) => {
                res.should.have.status(200);

                const shorter = JSON.parse(res.text).shortDomain;
                chai.request(Server).get('/short-to-long?query=' +
                    JSON.stringify({shortDomain: shorter}))
                    .end((err, res) => {
                        res.should.have.status(200);
                    });
            });
        done();
    });

    it('should get long domain by short from db', (done) => {
        chai.request(Server).post('/long-to-short')
            .send({longDomain: "www.google.com"})
            .end(async (err, res) => {
                res.should.have.status(200);

                const shorter = JSON.parse(res.text).shortDomain;
                await RedisClient.flushAll();
                chai.request(Server).get('/short-to-long?query=' +
                    JSON.stringify({shortDomain: shorter}))
                    .end((err, res) => {
                        res.should.have.status(200);
                    });
            });
        done();
    });
});