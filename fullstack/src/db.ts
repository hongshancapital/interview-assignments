import Db from "mysql2-async";
import * as dotenv from "dotenv";
dotenv.config();

const db_config = {
  host: process.env.DB_HOST,
  port: parseInt(process.env.DB_PORT),
  user: process.env.DB_USER,
  password: process.env.DB_PWD,
  database: process.env.DB_NAME,
};

const db = new Db(db_config);

export const findLongUrlById = async (id: number) => {
  const queryString = `
        SELECT long_url FROM Url WHERE id=?
        `;
  const longUrl = await db.getval(queryString, [id]);
  console.log("longUrl:", longUrl);
  return longUrl;
};

export const findIdByLongUrl = async (longUrl: string): Promise<number> => {
  const queryString = `
        SELECT id FROM Url WHERE long_url=?
        `;
  const id: number = await db.getval(queryString, [longUrl]);
  console.log("id:", id);
  return id;
};

export const save = async (longUrl: string) => {
  const queryString = `
        INSERT INTO Url (long_url) VALUES (?)
        `;
  const insertId = await db.insert(queryString, [longUrl]);
  console.log("insertId:", insertId);
  return insertId;
};
