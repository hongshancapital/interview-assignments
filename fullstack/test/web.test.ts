import request from "supertest";
import app from "../src/server/app";

describe("GET /", () => {
    it("should return 200 OK", () => {
        return request(app).get("/")
            .expect(200);
    });

    it("should return 200 OK", () => {
        return request(app).get("/dasdasd")
            .expect(500);
    });
});
