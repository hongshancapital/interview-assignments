"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const controllers_1 = __importDefault(require("../../controllers"));
exports.default = (router) => {
    router
        .post('/urls', controllers_1.default.url.saveUrlCont)
        .get('/urls', controllers_1.default.url.getLongUrlByShortCont);
};
//# sourceMappingURL=shorturl.js.map