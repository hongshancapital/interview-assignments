import { logger } from "./logger";

const redis = require("redis");

export class DBProxy {
  private clientNo: string;
  private client: any;
  constructor() {
    this.clientNo = "DBProxy" + Math.ceil(Math.random() * 9999);
  }

  public async connect() {
    if (!this.client) {
      this.client = redis.createClient({
        socket: {
          host: "127.0.0.1",
          port: 6379,
        },
      });
      this.client.on("error", function (err: any) {
        console.log("Error " + err);
      });
    }
    await this.client.connect();
  }

  public async disconnect() {
    await this.client.disconnect();
  }

  public async set(key: string, value: string) {
    logger.debug(
      this.clientNo + `: dbproxy set: key=[${key}] value=[${value}]`
    );
    try {
      const res = await this.client.set(key, value);
      return res;
    } catch (error) {
      logger.error(`DBProxy error: ${this.clientNo} - ${error}`);
      return;
    }
  }

  public async get(key: string) {
    logger.debug(this.clientNo + `: dbproxy get: key=[${key}]`);
    try {
      const res = await this.client.get(key);
      return res;
    } catch (error) {
      logger.error(`DBProxy error: ${this.clientNo} - ${error}`);
      return;
    }
  }
}
