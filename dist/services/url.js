"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const url_1 = __importDefault(require("../models/url"));
const stringUtils_1 = __importDefault(require("../utils/stringUtils"));
const config_1 = __importDefault(require("config"));
const saveUrl = async (longurl) => {
    let existedUrl = await url_1.default.findOne({ url: longurl }, { shorturl: 1, _id: 0 });
    if (existedUrl) {
        return existedUrl.shorturl;
    }
    const count = await url_1.default.count();
    const SERVER_URL = config_1.default.get('SERVER_URL');
    const shorturl = `${SERVER_URL}/${stringUtils_1.default.getStr62ByNumber(count)}`;
    let urlItem = await url_1.default.create({
        shorturl: shorturl,
        url: longurl,
        createTime: new Date()
    });
    return urlItem.shorturl;
};
const getLongUrlByShort = async (shorturl) => {
    let itemUrl = await url_1.default.findOne({ shorturl: shorturl });
    if (itemUrl) {
        return itemUrl.url;
    }
    return "";
};
exports.default = { saveUrl, getLongUrlByShort };
//# sourceMappingURL=url.js.map