import errorHandler from "errorhandler";
require("dotenv").config();
import LevelDb from "./db/db";
import app from "./app";
import logger from "./util/logger";
import { connectRedis, initCache } from "./db/cache";

const redis = require("redis");
const level = require("level");

// connect database, only use for development
try {
  LevelDb.idTable = level("/data/id", { valueEncoding: "json" });
  LevelDb.urlTable = level("/data/url", { valueEncoding: "json" });
} catch (e) {
  logger.error("DB connetion error:" + e.stack);
  process.exit(1);
}

// connect to Redis, and init cache
try {
  connectRedis({ port: 6379, host: process.env.REDIS_HOST });
  LevelDb.getCurrentId().then(initCache);
} catch (e) {
  logger.error("Cache connetion/init error:" + e.stack);
  process.exit(1);
}

process.on("unhandledRejection", (reason, promise:Promise<any>) => {
    console.log(promise)
    logger.error("unhandledRejection:" +  '- reason:'+ reason );
    process.exit(1);
  });
  
process.on("uncaughtException", (e) => {
  logger.error("uncaughtException:" + e.stack);
  process.exit(1);
});


/**
 * Error Handler. Provides full stack
 */
if (process.env.NODE_ENV === "development") {
  app.use(errorHandler());
}

/**
 * Start Express server.
 */
const server = app.listen(app.get("port"), () => {
  console.log(
    "  App is running at http://localhost:%d in %s mode",
    app.get("port"),
    app.get("env")
  );
  console.log("  Press CTRL-C to stop\n");
});


export default server;
