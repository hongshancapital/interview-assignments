import "dotenv/config";
import express, { NextFunction, Request, Response } from "express";
import urlRouter from "./routers/url"
import createHttpError, { isHttpError } from "http-errors";
import { BizError, ErrorCodeOk } from "./biz/errors/bizerror";
import { responseJson } from "./util/response";
import { logger } from "./util/log";

const app = express();
app.use(express.json());
app.use("/url", urlRouter);

const error404 = createHttpError(404, "Endpoint not found");

app.use((req, res, next) => {
    next(error404);
});

// 错误处理
// eslint-disable-next-line @typescript-eslint/no-unused-vars
app.use((error: unknown, req: Request, res: Response, next: NextFunction) => {
    logger.error(error);
    let message = "An unknown error occurred";
    let httpStatusCode = 500;
    let bizErrorCode = ErrorCodeOk;

    if (error instanceof BizError) {
        httpStatusCode = 200;
        message = error.message;
        bizErrorCode = error.code;
    } else if (isHttpError(error)) {
        httpStatusCode = error.status;
        message = error.message;
    }

    res.status(httpStatusCode).json(responseJson(message, bizErrorCode));
});

export default app;