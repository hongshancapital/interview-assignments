const express = require("express");
import { ShortUrlSaveBLogic } from "../../blogic/url/save";
import { ShortUrlGetBLogic } from "../../blogic/url/get";
import { BaseRouter } from "../base-router";
export const router = express.Router();

// 短域名服务相关接口
BaseRouter.post(router, "/save", ShortUrlSaveBLogic);
BaseRouter.post(router, "/get", ShortUrlGetBLogic);
