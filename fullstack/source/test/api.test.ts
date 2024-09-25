import request from "supertest";
import app from "../src/app"

describe("GET /save", () => {
    it("GET /save", () => {
        return request(app).get("/host/save?longHost=62").expect({ code: 0, message: '', data: { longHost: '62', shortHost: 'C' } });
    })
})

describe("GET /search", () => {
    it("GET /search", () => {
        return request(app).get("/host/save?longHost=62").expect({ code: 0, message: '', data: { longHost: '62', shortHost: 'C' } });
    })
})