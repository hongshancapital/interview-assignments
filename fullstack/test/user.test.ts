import request from "supertest";
import app from "../src/app";

var cookie:any;

describe("GET /login", () => {
    it("should return", (done) => {
        request(app).get("/login").end(function(err, res) {
            done();
            });
        request(app).post("/login").send({email: 'john@qq.com',password:"213"}).end(function(err, res) {
                cookie = res.header['set-cookie'];
            });
        request(app).get("/login").set('Cookie', cookie).end(function(err, res) {
            done();
            });
    })
});

describe("POST /login", () => {
    it("should return ", (done) => {
        const res=request(app).post("/login")
        .send({email: 'john@qq.com',password:"213"}).end(function(err, res) {
            if (!err) {
              done();
            }
          });

    });
});
