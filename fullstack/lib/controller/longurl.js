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
exports.getLongUrl = void 0;
const mongoose_1 = __importDefault(require("mongoose"));
const cache_1 = require("../service/cache");
const Slink = mongoose_1.default.model('Slink');
const getLongUrl = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { id } = req.params;
    if (!id) {
        return res.status(400).json({ data: 'id为必传字段' });
    }
    const result = (yield Slink.findOne({ id }));
    const cacheVal = {
        id: result.id,
        url: result.url,
    };
    (0, cache_1.cacheSet)(id, JSON.stringify(cacheVal));
    res.status(200).json({
        code: 0,
        message: 'success',
        data: {
            url: result.url,
        },
    });
});
exports.getLongUrl = getLongUrl;
