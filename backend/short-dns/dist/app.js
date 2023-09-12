"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
var express_1 = __importDefault(require("express"));
var env_1 = require("./config/env");
var router_1 = __importDefault(require("./controller/router"));
var app = (0, express_1.default)();
var PORT = env_1.SERVER_PORT;
var r = express_1.default.Router();
app.use(express_1.default.json());
app.use(express_1.default.urlencoded({ extended: true }));
app.use('/s', router_1.default);
app.listen(PORT);
