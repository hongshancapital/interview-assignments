/*
 * @Author: zhangyan
 * @Date: 2023-03-10 17:58:46
 * @LastEditTime: 2023-03-10 18:09:16
 * @LastEditors: zhangyan
 * @FilePath: /fullstack/test/db.spec.ts
 * @Description: mongoDb test
 */
import Db from "../src/plugin/db";
import dbconfig from "../config/dbconfig";

describe("mongoDb test", () => {
  it("test db connect", async () => {
    const db = await Db.getInstance().connection();
    expect(db).not.toBeNull();
  });

  it("test db find", async () => {
    await Db.getInstance()
      .find(dbconfig.collectionName, { token: "ty146SG" })
      .then((data) => {
        expect(data.length).toBe(1);
      });
  });

  it("test db count", async () => {
    await Db.getInstance()
      .count(dbconfig.collectionName, { token: "ty146SG" })
      .then((data) => {
        expect(data).toBe(1);
      });
  });
});
