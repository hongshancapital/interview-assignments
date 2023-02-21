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
const node_fetch_1 = __importDefault(require("node-fetch"));
test('获取短链长度等于8(去除域名)', () => __awaiter(void 0, void 0, void 0, function* () {
    const response = yield (0, node_fetch_1.default)("http://localhost:5000/api/url/shorten", {
        method: 'POST',
        body: JSON.stringify({
            longUrl: "https://www.google.com"
        }),
        headers: {
            'Content-Type': 'application/json',
            Accept: 'application/json',
        },
    });
    if (!response.ok) {
        const e = new Error(`error! status: ${response.status}`);
        expect(e).toMatch('error');
    }
    const result = (yield response.json());
    expect(result.code.length).toBe(8);
}));
test('传空域名，正常触发报错提示', () => __awaiter(void 0, void 0, void 0, function* () {
    expect.assertions(1);
    const response = yield (0, node_fetch_1.default)("http://localhost:5000/api/url/shorten", {
        method: 'POST',
        body: JSON.stringify({
            longUrl: ""
        }),
        headers: {
            'Content-Type': 'application/json',
            Accept: 'application/json',
        },
    });
    if (!response.ok) {
        const e = new Error(`error! status: ${response.status}`);
        expect(e).toMatch('error');
    }
}));
test('传特殊字符域名，正常触发报错提示', () => __awaiter(void 0, void 0, void 0, function* () {
    expect.assertions(1);
    const response = yield (0, node_fetch_1.default)("http://localhost:5000/api/url/shorten", {
        method: 'POST',
        body: JSON.stringify({
            longUrl: "https://www.google*&^.com"
        }),
        headers: {
            'Content-Type': 'application/json',
            Accept: 'application/json',
        },
    });
    if (!response.ok) {
        const e = new Error(`error! status: ${response.status}`);
        expect(e).toMatch('error');
    }
}));
test('传非域名，正常触发报错提示', () => __awaiter(void 0, void 0, void 0, function* () {
    expect.assertions(1);
    const response = yield (0, node_fetch_1.default)("http://localhost:5000/api/url/shorten", {
        method: 'POST',
        body: JSON.stringify({
            longUrl: "hsasdxc"
        }),
        headers: {
            'Content-Type': 'application/json',
            Accept: 'application/json',
        },
    });
    if (!response.ok) {
        const e = new Error(`error! status: ${response.status}`);
        expect(e).toMatch('error');
    }
}));
