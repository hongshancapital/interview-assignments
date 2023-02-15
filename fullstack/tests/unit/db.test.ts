//import { describe, expect, jest, it, beforeAll, afterAll, beforeEach } from "@jest/globals";
const {MongoClient} = require('mongodb');

describe("check mongodb operations", () => {
  let connection;
  let db;

  beforeAll(async () => {
    connection = await MongoClient.connect('mongodb://localhost:27017/jest_unittest', {
      useNewUrlParser: true,
      useUnifiedTopology: true,
    });
    db = await connection.db();
  });

  afterAll(async () => {
    await connection.close();
  });

  beforeEach(async () => {
    await db.collection('jest_unittest').deleteMany({});
    await db.collection('another-jest-test').deleteMany({});
  });

  it("should insert a url doc into collection", async () => {
    const urls = db.collection("jest_unittest");

    const mockUrl = { _id: "mock-url-id", rawUrl: "http://www.somedomain.com/api/v1/test" };
    await urls.insertOne(mockUrl);

    const insertedUrl = await urls.findOne({ _id: "mock-url-id" });
    expect(insertedUrl).toEqual(mockUrl);
  });

  it('should insert multi docs into collection', async () => {
    const collection = db.collection('another-jest-test');

    await collection.insertMany([{a: 1}, {b: 2}, {c: 3}]);
    const count = await collection.estimatedDocumentCount({});

    expect(count).toBe(3);
  });
});
