"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.getLongUrlByShortCont = exports.saveUrlCont = void 0;
const url_1 = __importDefault(require("../services/url"));
const saveUrlCont = async (ctx, next) => {
    const longurl = ctx.request.body.url;
    let shorturl = await url_1.default.saveUrl(longurl);
    ctx.body = {
        code: 200,
        msg: 'success',
        data: {
            shorturl
        }
    };
    return next();
};
exports.saveUrlCont = saveUrlCont;
const getLongUrlByShortCont = async (ctx, next) => {
    let shorturl = ctx.request.query.shorturl;
    let longurl = await url_1.default.getLongUrlByShort(shorturl);
    ctx.body = {
        code: 200,
        msg: 'success',
        data: longurl
    };
    return next();
};
exports.getLongUrlByShortCont = getLongUrlByShortCont;
//# sourceMappingURL=url.js.map