import express from "express";
import log4js from "log4js";
import loggerConfig from "./config/logger";
import hostController from "./controller/hostController";


const app = express();

log4js.configure(loggerConfig);

const logger = log4js.getLogger("app");

app.use(log4js.connectLogger(logger, {}));
app.use(express.json());
app.use(express.urlencoded({
  extended: false
}));

app.use("/host", hostController);

export default app;