import request from "supertest";
import app from "../src/app";
let userCookie:any;
beforeEach(function(done) {
    const res=request(app).post("/login")
    .send({email: 'john@qq.com',password:"213"}).end(function(err, res) {
        if (!err) {
          userCookie = res.header['set-cookie'];
          done();
        }
      });
  });

describe("GET /domain", () => {
    it("should return ", (done) => {
        const res = request(app).get("/domain").set('Cookie',userCookie)
        .end(function(err, res) {
          if (!err) {
            done();
          }
        });
    })
});

describe("POST", () => {
    it("/tiny", (done) => {
        request(app).post("/domain/tiny").set('Cookie',userCookie)
            .send({longurl:'http://test.com'}).end(function(err, res) {
                if (!err) {
                  done();
                }
              });
        request(app).post("/domain/tiny").set('Cookie',userCookie)
        .send({longurl:`http://${Math.random().toString(36).slice(-8)}.com`}).end(function(err, res) {
            if (!err) {
              done();
            }
          });
        request(app).post("/domain/huge").set('Cookie',userCookie)
          .send({shorturl:'http://test.com/2321'}).end(function(err, res) {
              if (!err) {
                done();
              }
            });
        request(app).post("/domain/huge").set('Cookie',userCookie)
          .send({shorturl:'http://192.168.31.37/¡$G{6'}).end(function(err, res) {
              if (!err) {
                done();
              }
            });
    });
});
