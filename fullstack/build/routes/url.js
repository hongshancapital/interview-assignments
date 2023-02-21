"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const valid_url_1 = __importDefault(require("valid-url"));
const url_1 = require("../models/url");
const baseUrl = "http://localhost:5000";
const router = express_1.default.Router();
router.post('/shorten', (req, res, next) => __awaiter(void 0, void 0, void 0, function* () {
    const { longUrl } = req.body;
    if (valid_url_1.default.isUri(longUrl)) {
        try {
            let url = yield url_1.UrlModel.findOne({ longUrl });
            if (url) {
                res.json({
                    shortUrl: `${baseUrl}/${url.urlCode}`,
                    code: url.urlCode
                });
            }
            else {
                // 唯一ID生成最好使用[雪花算法]，更符合高并发和大数据的需求
                // 这里使用简单的生成方式，10000k以内数据没有重复
                const urlCode = Math.random().toString(36).slice(-8);
                url = new url_1.UrlModel({
                    longUrl,
                    urlCode
                });
                yield url.save();
                res.json({
                    shortUrl: `${baseUrl}/${urlCode}`,
                    code: urlCode
                });
            }
        }
        catch (error) {
            res.status(500).json('Server error');
        }
    }
    else {
        res.status(401).json('Invalid long url');
    }
}));
exports.default = router;
