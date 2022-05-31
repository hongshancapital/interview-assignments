import express from "express";
import toolbox from "./toolbox";
import slink from "./slink";

const apiRouter = express.Router();

apiRouter.use(toolbox, slink);

export default apiRouter;
