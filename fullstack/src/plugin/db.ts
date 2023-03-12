/*
 * @Author: zhangyan
 * @Date: 2023-03-09 16:30:53
 * @LastEditTime: 2023-03-12 00:16:38
 * @LastEditors: zhangyan
 * @FilePath: /fullstack/src/plugin/db.ts
 * @Description: mongoDb数据库相关操作封装
 */
import { MongoClient } from "mongodb";
import dbconfig from "../../config/dbconfig";

const dbUrl = dbconfig.dbUrl;

const options = {
  connectTimeoutMS: 5000,
};

interface DBI {
  insert<T>(collectionName: string, doc: T): Promise<boolean>;
  find(collectionName: string, filter: object): Promise<any[]>;
  count(collectionName: string, filter: object): Promise<number>;
}

class Db implements DBI {
  public client: MongoClient | undefined;
  private static instance: Db;

  static getInstance() {
    if (!Db.instance) {
      Db.instance = new Db();
    }
    return Db.instance;
  }

  constructor() {}

  connection(): Promise<MongoClient> {
    return new Promise<MongoClient>((resolve, reject) => {
      if (!this.client) {
        MongoClient.connect(dbUrl, options).then(
          (client) => {
            console.log("db server connect success!");
            this.client = client;
            resolve(client);
          },
          (err) => {
            reject(err);
          }
        );
      } else {
        resolve(this.client);
      }
    });
  }

  insert<T>(collectionName: string, doc: T): Promise<boolean> {
    return new Promise<boolean>((resolve, reject) => {
      this.connection().then((client) => {
        const db = client.db();
        const result = db
          .collection(collectionName)
          .insertOne(doc as any)
          .then(
            () => {
              resolve(true);
            },
            (err) => {
              resolve(false);
              reject(err);
            }
          );
      });
    });
  }

  find(collectionName: string, filter: object): Promise<any[]> {
    return new Promise<any[]>((resolve, reject) => {
      this.connection().then((client) => {
        const db = client.db();
        const result = db.collection(collectionName).find(filter);
        result.toArray().then(
          (docs) => {
            resolve(docs);
          },
          (err) => {
            reject(err);
          }
        );
      });
    });
  }

  count(collectionName: string, filter: object): Promise<number> {
    return new Promise<number>((resolve, reject) => {
      this.connection().then((client) => {
        const db = client.db();
        const result = db.collection(collectionName).countDocuments(filter);
        result.then(
          (count) => {
            resolve(count);
          },
          (err) => {
            reject(err);
          }
        );
      });
    });
  }
}

export default Db;
