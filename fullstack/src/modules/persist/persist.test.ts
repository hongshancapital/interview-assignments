import { createConnection } from "mongoose";
import { config } from "../../config";
import { TinyURLSchema } from "./persist.model";
import { PersistService } from "./persist.service";

describe("Persist Module", () => {
  const connection = createConnection(config.PERSIST_MONGODB_URI);
  const model = connection.model("test_persist_module", TinyURLSchema);
  const TTL = 1000 * 60;
  const persistService = new PersistService(model, TTL);

  beforeEach(async () => {
    await model.deleteMany();
  });

  afterAll(async () => {
    await connection.dropDatabase();
    await connection.close();
  });

  it("写入数据", async () => {
    const alias = "00000105";
    const url = "https://example.com";
    const expireAt = Date.now() + TTL;

    await persistService.set(alias, url);

    const doc = await model.findOne({ alias });
    if (!doc) {
      fail("写入失败");
    }

    expect(doc.url).toBe(url);
    expect(Math.abs(expireAt - doc.expireAt) < 100).toBe(true);
  });
});
