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
Object.defineProperty(exports, "__esModule", { value: true });
const request = require('supertest');
const util_1 = require("../../utilites/util");
const baseURL = 'http://localhost:3003';
describe('POST /api', () => {
    const longUrl = {
        origUrl: "https://www.makeuseof.com/express-apis-jest-test/"
    };
    it("long url return short url", () => __awaiter(void 0, void 0, void 0, function* () {
        var _a;
        const response = yield request(baseURL).post("/api").send(longUrl);
        expect((_a = response.statusCode) === null || _a === void 0 ? void 0 : _a.toString()[0]).toBe('2');
        expect((0, util_1.isShortDomain)(response.text)).toBeTruthy();
    }));
    const shortUrl1 = {
        origUrl: "https://shorturl.at/k0qDocVp"
    };
    it("short url in database return long url", () => __awaiter(void 0, void 0, void 0, function* () {
        var _b;
        const response = yield request(baseURL).post("/api").send(shortUrl1);
        expect((_b = response.statusCode) === null || _b === void 0 ? void 0 : _b.toString()[0]).toBe('2');
        expect((0, util_1.isLongDomain)(response.text)).toBeTruthy();
    }));
    const shortUrl2 = {
        origUrl: "https://shorturl.at/0000444"
    };
    it("short url not in database return short url", () => __awaiter(void 0, void 0, void 0, function* () {
        var _c;
        const response = yield request(baseURL).post("/api").send(shortUrl2);
        expect((_c = response.statusCode) === null || _c === void 0 ? void 0 : _c.toString()[0]).toBe('2');
        expect((0, util_1.isShortDomain)(response.text)).toBeTruthy();
    }));
    const invalidUrl = {
        origUrl: "https 1234567890"
    };
    it("invalid url return invalid url", () => __awaiter(void 0, void 0, void 0, function* () {
        var _d;
        const response = yield request(baseURL).post("/api").send(invalidUrl);
        expect((_d = response.statusCode) === null || _d === void 0 ? void 0 : _d.toString()[0]).toBe('2');
        expect((0, util_1.validateUrl)(response.text)).toBeFalsy();
    }));
});
