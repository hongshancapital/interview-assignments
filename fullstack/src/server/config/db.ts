import mongoose, { Connection, Mongoose } from "mongoose";
import { config } from "./config";
import { IConfig } from "../interfaces/IConfig";
import { logger } from "../utils/logger";

/**
 * mongoose db configuration
 */
class DbConfig {
  private readonly _config: IConfig;
  private readonly _mongo: Mongoose;

  constructor(config: IConfig, mongo: Mongoose) {
    this._config = config;
    this._mongo = mongo;
  }

  dbConnection(): Mongoose {
    const {
      mongodb: { url, port, collection, options },
    } = this._config;
    const mongoURL = `mongodb://${url}:${port}/${collection}`;
    this._mongo.connect(mongoURL, options);
    const db: Connection = this._mongo.connection;
    db.on(
      "error",
      logger.error.bind(
        logger,
        "Database_Connection",
        "got error when executing DB connection"
      )
    );
    db.once("open", () => {
      logger.info("DB connected", "");
    });
    return mongoose;
  }

  get mongo() {
    return this._mongo;
  }

  get config() {
    return this._config;
  }
}

export default Object.freeze(new DbConfig(config, mongoose));
