"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const uuid_1 = require("uuid");
const db_1 = __importDefault(require("../config/db"));
class DBConnect {
    insertDatabase(longDomain, shortDomain, handleResult) {
        const sql = `insert into urlTable  (ID, longDomain, shortDomain) values ('${(0, uuid_1.v4)()}', '${longDomain}', '${shortDomain}');`;
        db_1.default.query(sql, function (err, data) {
            handleResult(err, data);
        });
    }
    findLongDomain(shortUrl, handleResult) {
        const sql = `select * from urlTable where shortDomain = '${shortUrl}'`;
        db_1.default.query(sql, function (err, data) {
            var _a;
            console.dir(data);
            handleResult(err, (_a = data[0]) === null || _a === void 0 ? void 0 : _a.longDomain);
        });
    }
    findShortDomain(longUrl, handleResult) {
        const sql = `select * from urlTable where longDomain = '${longUrl}'`;
        db_1.default.query(sql, function (err, data) {
            var _a;
            handleResult(err, (_a = data[0]) === null || _a === void 0 ? void 0 : _a.shortDomain);
        });
    }
}
exports.default = DBConnect;
