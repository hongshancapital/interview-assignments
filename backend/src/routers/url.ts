import express from "express";
import * as UrlController from "../controllers/url"
import { createRateLimiter } from "../middleware/ratelimiter";
import env from "../util/validateEnv";

const urlRouter = express.Router();

console.log(`env.RATE_LIMIT_URL_POST, env.RATE_LIMIT_URL_POST_WINDOW_SEC: ${env.RATE_LIMIT_URL_POST}, ${env.RATE_LIMIT_URL_POST_WINDOW_SEC}`)

urlRouter.post("/",
    createRateLimiter(env.RATE_LIMIT_URL_POST_WINDOW_SEC, env.RATE_LIMIT_URL_POST),
    UrlController.addNewUrl
);

urlRouter.get("/:short",
    createRateLimiter(env.RATE_LIMIT_URL_GET_WINDOW_SEC, env.RATE_LIMIT_URL_GET),
    UrlController.redirectOriginUrl
);

export default urlRouter;