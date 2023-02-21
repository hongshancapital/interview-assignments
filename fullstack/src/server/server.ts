import { app } from "./app";
import { logger } from "./utils/logger";
import DBConnect from "./config/db";

// connect to mongodb
DBConnect.dbConnection();

const port = app.get("port");
const server = app.listen(port, () => {
  logger.info(
    "url shorten App started=>",
    `App is running at http://localhost:${port}`
  );
});

process.on("unhandledRejection", (reason) => {
  logger.error("Unhandled_Rejection", reason);
});

process.on("uncaughtException", (error) => {
  logger.error("Uncaught_Exception", error);
});

export default server;
