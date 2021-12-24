"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.transformReqShortUrl = exports.calSingleCharSub = void 0;
const constants_1 = require("../constants");
function calSingleCharSub(a, b) {
    return a.charCodeAt(0) - b.charCodeAt(0);
}
exports.calSingleCharSub = calSingleCharSub;
function transformReqShortUrl(originShortUrl) {
    try {
        const url = new URL(originShortUrl);
        const pathname = url.pathname;
        const pathnameValue = pathname.substring(1);
        const hashFlag = pathnameValue[0];
        const realShortUrl = pathnameValue.substring(1);
        const hostname = url.hostname;
        return {
            pathnameValue,
            hashFlag,
            realShortUrl,
            hostname
        };
    }
    catch (e) {
        throw new Error(JSON.stringify(Object.assign(Object.assign({}, constants_1.ERROR.INVALID_SHORTURL), { message: e.message })));
    }
}
exports.transformReqShortUrl = transformReqShortUrl;
//# sourceMappingURL=index.js.map