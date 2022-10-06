"use strict";
var __createBinding = (this && this.__createBinding) || (Object.create ? (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    var desc = Object.getOwnPropertyDescriptor(m, k);
    if (!desc || ("get" in desc ? !m.__esModule : desc.writable || desc.configurable)) {
      desc = { enumerable: true, get: function() { return m[k]; } };
    }
    Object.defineProperty(o, k2, desc);
}) : (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    o[k2] = m[k];
}));
var __setModuleDefault = (this && this.__setModuleDefault) || (Object.create ? (function(o, v) {
    Object.defineProperty(o, "default", { enumerable: true, value: v });
}) : function(o, v) {
    o["default"] = v;
});
var __importStar = (this && this.__importStar) || function (mod) {
    if (mod && mod.__esModule) return mod;
    var result = {};
    if (mod != null) for (var k in mod) if (k !== "default" && Object.prototype.hasOwnProperty.call(mod, k)) __createBinding(result, mod, k);
    __setModuleDefault(result, mod);
    return result;
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const fnv = __importStar(require("fnv-plus"));
const body_parser_1 = __importDefault(require("body-parser"));
const app = (0, express_1.default)();
let map = new Map();
app.use(body_parser_1.default.json());
app.post('/add', (req, res) => {
    let longUrl = req.body.url;
    if (!longUrl) {
        res.send("The long url add error");
    }
    else {
        let shortUrl = fnv.fast1a32hex(longUrl);
        if (!map.has(shortUrl)) {
            map.set(shortUrl, longUrl);
        }
        res.send(shortUrl);
    }
});
app.get('/short/:url', (req, res) => {
    let shortUrl = req.params.url;
    if (map.has(shortUrl)) {
        let longUrl = map.get(shortUrl);
        res.redirect(301, "http://localhost:7777/long/" + longUrl);
    }
    else {
        res.send("This short url not found");
    }
});
app.get("/long/:url", (req, res) => {
    res.send("This is real long url:" + req.params.url);
});
app.listen(7777, function () {
    console.log('App listening on port 7777!');
});
