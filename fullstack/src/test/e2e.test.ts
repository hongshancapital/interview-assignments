
import supertest from "supertest";

import { PrismaClient } from "@prisma/client";

import { App } from "../app";
import { URLStore } from "../store/URLStore";

const prisma = new PrismaClient();
let app = new App().start();
const urlStore =  new URLStore();

beforeAll( () => {
  //urlStore.addUrlPairsCache("1885fabb","http://www.google.com/search?q=testdup2")
}) 


afterAll(async () => {
  await prisma.urlTable.deleteMany({
    where: {shortUrl: "ed39e267"}
  });
  await prisma.$disconnect()
})

describe("POST /long2short", () => {

  it("bad URI miss http", async () => {
    return await supertest(app)
      .post("/long2short")
      .send({
        url: "www.google.com/search?q=java",
      })
      .expect(400)
      .then((res) => {
        expect(res.body).toMatchObject({ data: "Invalid URL" });
      });
  });

  it("bad URI no url para", async () => {
    return await supertest(app)
      .post("/long2short")
      .send({
        url2: "www.google.com/search?q=java2",
      })
      .expect(400)
      .then((res) => {
        expect(res.body).toMatchObject({ data: "Invalid URL" });
      });
  });


  it("should return shortLink OK", async () => {
    return await supertest(app)
      .post("/long2short")
      .send({
        url: "http://www.google.com/search?q=test",
      })
      .expect(200)
      .then((res) => {
        expect(res.body).toMatchObject({ data: "ed39e267" });
      });
  });





});


describe("GET /short2long", () => {

  it("should return longLink OK", async () => {
    return await supertest(app)
      .get("/short2long/ed39e267")
      .expect("Content-Type", /json/)
      .expect(200)
      .then((res) => {
        expect(res.body).toMatchObject({
          data: "http://www.google.com/search?q=test",
        });
      });
  });

  it("short id is null", async () => {
    return await supertest(app)
      .get("/short2long/null")
      .expect(400)
      .then((res) => {
        expect(res.body).toMatchObject({
          data: "Invalid short URL",
        });
      });
  });

  it("short id less than 8", async () => {
    return await supertest(app)
      .get("/short2long/e66c687")
      .expect("Content-Type", /json/)
      .expect(400)
      .then((res) => {
        expect(res.body).toMatchObject({
          data: "Invalid short URL",
        });
      });
  });

  it("short url not exist return 400", async () => {
    return await supertest(app)
      .get("/short2long/e66c687f")
      .expect("Content-Type", /json/)
      .expect(400)
      .then((res) => {
        expect(res.body).toMatchObject({
          data: "Invalid short URL",
        });
      });
  });
});

/*
describe("POST /long HASKKEY DUP", () => {
it("should return shortLink OK with HASHKEY", async () => {
  urlStore.addUrlPairsCache("1885fabb","http://www.google.com/search?q=testdup2")
  return await supertest(app)
    .post("/long2short")
    .send({
      url: "http://www.google.com/search?q=testdup", //1885fabb
    })
    .expect(200)
    .then((res) => {
      expect(res.body).toMatchObject({ data: "812cb78f" }); // http://www.google.com/search?q=testdup[HASHKEY]
    });
}); 
});
*/

