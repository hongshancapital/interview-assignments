import { createConnection } from "mongoose";
import { config } from "../../config";
import { to62 } from "../../utils/to62";
import { IDSchema } from "./id.model";
import { IDService } from "./id.service";

describe("ID Module", () => {
  const NODE_ID = 3;
  const connection = createConnection(config.PERSIST_MONGODB_URI);
  const model = connection.model("test_id_module", IDSchema);
  const idService = new IDService(NODE_ID, model);

  beforeEach(async () => {
    await model.deleteMany();
  });

  afterAll(async () => {
    await connection.dropDatabase();
    await connection.close();
  });

  it("生成 ID", async () => {
    await model.updateOne(
      { nodeId: NODE_ID },
      { $set: { count: 9999 } },
      { upsert: true }
    );

    const alias = await idService.generate();
    expect(alias).toBe(
      `${to62(9999 + 1).padStart(6, "0")}${to62(NODE_ID).padStart(2, "0")}`
    );
  });
});
