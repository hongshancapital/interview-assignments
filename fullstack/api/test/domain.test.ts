import request from "supertest";
import { connectDb, } from "../src/db"
import { app } from "../src/app";
import { getConnection, IsNull, Not } from "typeorm";
import { Domain } from "../src/entity/domain";

beforeAll(async () => {
    await connectDb();
})

afterAll(async () => {
    await getConnection().getRepository(Domain).delete({ id: Not(IsNull()) });
    await getConnection().close();
})

describe("Test api", () => {
    test("index should return ok", () => {
        request(app)
            .get("/")
            .then((res: any) => {
                expect(res.statusCode).toBe(200);
                expect(res.text).toBe("ok");
            });
    });

    test("domain to url works", async () => {
        const domain = "http://www.baidu.com";
        getConnection().getRepository(Domain).delete({ domain:domain });

        const res = await request(app).post(`/api/domain/domain_to_url`).send({ domain });
        expect(res.statusCode).toBe(200);
        expect(res.body.status).toBe("success");
        expect(res.body.results.id.length).toBe(8);

        const res2 = await request(app).post(`/api/domain/domain_to_url`).send({ domain });
        expect(res2.statusCode).toBe(200);
        expect(res2.body.status).toBe("success");
        expect(res2.body.results.id.length).toBe(8);

    });

    test("url to domain works", async () => {
        await getConnection().getRepository(Domain).save({ id: "za7byYWj", domain: "http://www.google.com", url: "za7byYWj" })

        const url = "za7byYWj";
        const res = await request(app).get(`/api/domain/url_to_domain/${url}`);
        expect(res.statusCode).toBe(200);
        expect(res.body.status).toBe("success");
        expect(res.body.results.id).toBe("za7byYWj");
        expect(res.body.results.domain.length).toBeGreaterThan(0);
    });
});