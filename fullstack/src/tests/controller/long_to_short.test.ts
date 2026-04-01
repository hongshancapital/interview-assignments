import chai from 'chai';
import chaiHttp from "chai-http";
import Server, {BASE_URL, GENERATE_LENGTH} from "../../server";

chai.use(chaiHttp);
chai.should();

describe("test store shorter file", () => {
    it("should get -1 wrong long name", (done) => {
        chai.request(Server).post("/long-to-short")
            .set({'Content-Type': 'application/json'})
            .send({longDomain: 'baidu'})
            .end((err, res) => {
                res.should.have.status(400);
            });
        done();
    });

    it("should get -2 BadRequest!", (done) => {
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
                const long = res.body.longDomain;
                shorter.length.should.equal(BASE_URL.length + GENERATE_LENGTH);
            });
        done();
    });
});
