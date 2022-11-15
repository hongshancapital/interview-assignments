"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.cacheSet = exports.cacheGet = void 0;
const lru_cache_1 = __importDefault(require("lru-cache"));
const cache = new lru_cache_1.default({
    max: 1,
    maxAge: 1000 * 30,
    updateAgeOnGet: true,
    maxSize: 10,
    length(value, key) {
        const strLen = key.length + JSON.stringify(value).length;
        // 按最糟情况换算成M
        return Math.ceil((strLen * 2) / (1024 * 1024));
    },
});
const cacheGet = (id) => {
    return cache.get(id);
};
exports.cacheGet = cacheGet;
const cacheSet = (id, info) => {
    cache.set(id, info);
};
exports.cacheSet = cacheSet;
