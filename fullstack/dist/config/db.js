"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const mysql_1 = __importDefault(require("mysql"));
exports.default = mysql_1.default.createConnection({
    host: "127.0.0.1",
    user: "yong",
    password: "scdt!23",
    port: 3306,
    database: "scdt"
});
