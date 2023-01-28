const mongoose = require("mongoose");
const request = require("supertest");

const server = require("../dist/index");


beforeEach(async () => {
  await mongoose.connect('mongodb://127.0.0.1/urlShortener', {
    useNewUrlParser: true, useUnifiedTopology: true
  });
});


afterEach(async () => {
  await mongoose.connection.close();
});


describe("GET /r/search", () => {
  it("should return all urls", async () => {
    const res = await request(server).get("/r/search");
    expect(res.statusCode).toBe(200);
    expect(res.body.length).toBeGreaterThan(0);
  });
});

describe("POST /w/shrink", () => {
  it("should create a shortUrl", async () => {
    
    const res = await request(server).post("/w/shrink").send({
      fullUrl: "http://www.baidu.com"
    });
    expect(res.statusCode).toBe(200);
    expect(res.body.fullUrl).toBe("http://www.baidu.com");
  });
});