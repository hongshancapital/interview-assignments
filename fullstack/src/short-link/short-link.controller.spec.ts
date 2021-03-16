import * as request from "supertest";
import { Test } from "@nestjs/testing";
import { ShortLinkController } from "./short-link.controller";
import { AppModule } from "../app.module";
import { INestApplication } from "@nestjs/common";

const URl = "http://baidu.com";

describe("ShortLinkController", () => {
  let app: INestApplication;
  beforeEach(async () => {
    const module = await Test.createTestingModule({
      imports: [AppModule]
    })
      .compile();
    app = module.createNestApplication();
    await app.init();
  });

  it("basic", async () => {
    let sourceRes = await request(app.getHttpServer())
      .post("/generateShortLink")
      .send({ url: URl });
    let res302 = await request(app.getHttpServer())
      .get("/" + sourceRes.body.data);
    expect(res302.headers.location).toBe(URl);
    expect(res302.headers["set-cookie"]).not.toBeNull();
  });
});
