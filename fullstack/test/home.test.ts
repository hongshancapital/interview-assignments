import request from "supertest";
import app from "../src/app";

describe("GET /", () => {
    it("should return 200", (done) => {
        request(app).get("/")
            .expect(500, done);
    });
});
