import { MongoClient } from "mongodb";

export default async function createConnection(connectionString: string) {
  const client = new MongoClient(connectionString);
  const conn = await client.connect();
  const db = conn.db("short_link");
  return db;
}
