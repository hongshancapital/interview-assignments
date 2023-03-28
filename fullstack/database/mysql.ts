import mysql from "mysql";
import { TinyURL } from "./model";
import { lengthValidator } from "../util/util";

const SHORTENED_URL_MAX_LENGTH = 40;
const ORIGINAL_URL_MAX_LENGTH = 400;

let connection: mysql.Connection;

interface DbConfig {
  host: string;
  user: string;
  password: string;
}

function dbConnect(config?: DbConfig): Promise<string | void> {
  if (
    !connection ||
    (connection.state !== "connected" && connection.state !== "authenticated")
  ) {
    connection = mysql.createConnection({
      host: (config && config.host) || "127.0.0.1",
      user: (config && config.user) || "root",
      password: (config && config.password) || "123456",
      multipleStatements: true,
    });

    const ret = new Promise<string | void>((resolve, reject) => {
      connection.connect((err) => {
        if (err) {
          reject("Failed to connect to database. " + err);
        } else {
          resolve("connect ok");
        }
      });
    });
    return ret;
  } else {
    return Promise.resolve("already connect");
  }
}

/**
 * dbInit
 * @description init database, create db "SHORTEN_URL" & table "TinyURL" if not exist
 */
function dbInit(): Promise<string | void> {
  const ret = new Promise<string | void>((resolve, reject) => {
    connection.query(
      "CREATE DATABASE IF NOT EXISTS SHORTEN_URL;USE SHORTEN_URL;CREATE TABLE IF NOT EXISTS TinyURL (ID BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,ShortenedURL VARCHAR(" +
        SHORTENED_URL_MAX_LENGTH.toString() +
        ") NOT NULL,OriginalURL VARCHAR(" +
        ORIGINAL_URL_MAX_LENGTH.toString() +
        ") NOT NULL,INDEX (ShortenedUrl, OriginalUrl));",
      (err: mysql.MysqlError) => {
        if (err) reject(err);

        resolve("init ok");
      },
    );
  }).catch((err) => "Failed to init db. " + err);
  return ret;
}

function closeDbConnection(): Promise<string> {
  const ret = new Promise<string>((resolve, reject) => {
    connection.end((err) => {
      if (err) {
        reject("Failed to close the database connection. " + err);
      } else {
        resolve("connect closed");
      }
    });
  }).catch((err) => "Failed to close db connection. " + err);
  return ret;
}

function insertTinyURL(
  shortenedURL: string,
  originalURL: string,
): Promise<boolean | string> {
  const ret = new Promise<boolean | string>((resolve, reject) => {
    let flag = lengthValidator(shortenedURL, SHORTENED_URL_MAX_LENGTH);
    flag = flag && lengthValidator(originalURL, ORIGINAL_URL_MAX_LENGTH);

    if (!flag) {
      reject("URL is too long or empty");
    }

    connection.query(
      "INSERT INTO TinyURL (ShortenedURL, OriginalURL) VALUES ('" +
        shortenedURL +
        "','" +
        originalURL +
        "')",
      (err: mysql.MysqlError, rows: any) => {
        if (err) reject(err);

        resolve(rows);
      },
    );
  }).catch((err) => "Failed to insert the shortened URL into db. " + err);
  return ret;
}

function findMaxIdInTinyURL(): Promise<number | string> {
  const ret = new Promise<number | string>((resolve, reject) => {
    connection.query(
      "SELECT * FROM TinyURL ORDER BY id DESC LIMIT 0, 1",
      (err: mysql.MysqlError, rows: TinyURL[]) => {
        if (err) reject(err);

        if (!rows || rows.length < 1) {
          resolve(0);
        } else {
          resolve(rows[0].ID);
        }
      },
    );
  }).catch((err) => "Failed to get the max id of table tiny_table. " + err);
  return ret;
}

function findOrignalURLByShort(shortenedURL: string): Promise<string | string> {
  const ret = new Promise<string | string>((resolve, reject) => {
    if (!lengthValidator(shortenedURL, SHORTENED_URL_MAX_LENGTH)) {
      reject("invalid short URL length");
    }
    connection.query(
      "SELECT OriginalURL FROM TinyURL WHERE ShortenedURL='" +
        shortenedURL +
        "'",
      (err: mysql.MysqlError, rows: { OriginalURL: string }[]) => {
        if (err) reject(err);

        if (!rows || rows.length < 1) {
          resolve("");
        } else {
          resolve(rows[0].OriginalURL);
        }
      },
    );
  }).catch((err) => "Failed to get the orignal URL from db. " + err);
  return ret;
}

function findShortenedURLByOrignal(
  originalURL: string,
): Promise<string | string> {
  const ret = new Promise<string | string>((resolve, reject) => {
    if (!lengthValidator(originalURL, ORIGINAL_URL_MAX_LENGTH)) {
      reject("invalid original URL length");
    }
    connection.query(
      "SELECT ShortenedURL FROM TinyURL WHERE OriginalURL='" +
        originalURL +
        "'",
      (err: mysql.MysqlError, rows: { ShortenedURL: string }[]) => {
        if (err) reject(err);

        if (!rows || rows.length < 1) {
          resolve("");
        } else {
          resolve(rows[0].ShortenedURL);
        }
      },
    );
  }).catch((err) => "Failed to get the shortened URL from db. " + err);
  return ret;
}

function emptyTinyURL() {
  connection.query("TRUNCATE TinyURL");
}

const funcSet = {
  dbConnect,
  dbInit,
  closeDbConnection,
  insertTinyURL,
  findMaxIdInTinyURL,
  findShortenedURLByOrignal,
  findOrignalURLByShort,
  emptyTinyURL,
};

export default funcSet;
