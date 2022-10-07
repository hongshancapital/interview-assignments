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
const assert_1 = require("assert");
const shortUrl_1 = __importDefault(require("../../services/shortUrl"));
let testUrlId;
describe('shortUrlService', function () {
    before(function () {
        return __awaiter(this, void 0, void 0, function* () {
            yield shortUrl_1.default.init_sync();
        });
    });
    describe('#createShortUrl()', function () {
        it('should return urlId', function () {
            return __awaiter(this, void 0, void 0, function* () {
                testUrlId = yield shortUrl_1.default.createShortUrl('http://baidu.com');
                (0, assert_1.equal)(testUrlId.length > 0, true);
            });
        });
    });
    describe('#queryUrl()', function () {
        it(`should return dbNo + urlId equal last test result`, function () {
            return __awaiter(this, void 0, void 0, function* () {
                const result = yield shortUrl_1.default.queryUrl('http://baidu.com');
                (0, assert_1.equal)(result, testUrlId);
            });
        });
    });
    describe('#queryShortUrl()', function () {
        it(`should return url equal http://baidu.com`, function () {
            return __awaiter(this, void 0, void 0, function* () {
                const result = yield shortUrl_1.default.queryShortUrl(testUrlId.substring(0, 2), testUrlId.substring(2));
                (0, assert_1.equal)(result, 'http://baidu.com');
            });
        });
    });
});
//# sourceMappingURL=shortUrl.js.map