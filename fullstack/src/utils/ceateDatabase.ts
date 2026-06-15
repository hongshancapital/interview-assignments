import createConnection from "./db";

(async () => {
  if (!process.env.CONNECT_URL) throw Error("ConnectUrl must be string");
  if (!process.env.DB_NAME) throw Error("Invalid database name");
  const client = await createConnection(process.env.CONNECT_URL);
  const db = await client.db(process.env.DB_NAME);
  const collection = await db.createCollection("links", {
    validator: {
      $jsonSchema: {
        bsonType: "object",
        title: "Link Item Validation",
        required: ["url", "short_url", "create_time"],
        properties: {
          url: {
            bsonType: "string",
            description: "'url' must be a string and is required",
          },
          short_url: {
            bsonType: "string",
            description: "'short_url' must be a string and is required",
          },
          create_time: {
            bsonType: "double",
            description: "'create_time' must be a int and is required",
          },
        },
      },
    },
  });
  await collection.createIndex(
    {
      short_link: 1,
    },
    {
      unique: true,
    }
  );
  await client.close();
})();
