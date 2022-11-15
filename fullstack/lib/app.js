"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const body_parser_1 = __importDefault(require("body-parser"));
require("./mongo");
const shorturl_1 = require("./controller/shorturl");
const longurl_1 = require("./controller/longurl");
const app = (0, express_1.default)();
app.use(body_parser_1.default.json());
app.post("/shorturl/create", shorturl_1.createShortUrl);
app.get("/longurl/get", longurl_1.getLongUrl);
exports.default = app;
