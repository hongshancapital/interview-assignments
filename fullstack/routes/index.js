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
const express = __importStar(require("express"));
const async_lock_1 = __importDefault(require("async-lock"));
const shortUrl_1 = __importDefault(require("../services/shortUrl"));
const config_1 = __importDefault(require("../config"));
const lock = new async_lock_1.default();
let router = express.Router();
shortUrl_1.default.init();
/**
 * 短域名读取接口：接受短域名信息，返回长域名信息
 * @param urlId 短域名id {string}
 * @returns url 长域名 {string}
 */
router.get('/api/shortUrl/:urlId', (req, res, next) => __awaiter(void 0, void 0, void 0, function* () {
    if (req.params.urlId && /^[a-zA-Z0-9]{8}$/.test(req.params.urlId)) {
        const dbNo = req.params.urlId.substring(1, 3);
        const urlId = req.params.urlId.substring(3, 8);
        const url = yield shortUrl_1.default.queryShortUrl(dbNo, urlId);
        res.json({ url });
    }
    else {
        res.json({ error: 'invalid params: urlId' });
    }
}));
/**
 * 短域名存储接口：接受长域名信息，返回短域名信息
 * @param url 长域名 {string}
 * @returns urlId 短域名id {string}
 */
router.post('/api/shortUrl', (req, res, next) => {
    lock.acquire('post_api_shortUrl', function () {
        return __awaiter(this, void 0, void 0, function* () {
            const url = req.body.url;
            let urlId = yield shortUrl_1.default.queryUrl(url);
            if (!urlId) {
                urlId = yield shortUrl_1.default.createShortUrl(url);
            }
            res.json({ urlId: config_1.default.partition_no + urlId });
        });
    }).catch(function (err) {
        console.log('lock post_api_shortUrl error', err);
    });
});
module.exports = router;
//# sourceMappingURL=index.js.map