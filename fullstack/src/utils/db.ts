import { MongoClient } from "mongodb";

export default async function createConnection(connectionString: string) {
  const client = new MongoClient(connectionString);
  const conn = await client.connect();
  return conn;
}
