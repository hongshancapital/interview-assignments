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
exports.createShortUrl = void 0;
const mongoose = require('mongoose');
const base62 = require('base-62.js');
const Slink = mongoose.model('Slink');
const createShortUrl = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { url } = req.body;
    if (!url) {
        return res.status(400).json({ data: 'url为必传字段' });
    }
    const link = new Slink();
    link.id = base62.encode(Math.floor(Date.now() / 24));
    link.url = url;
    const result = yield link.save();
    res.status(200).json({
        code: 0,
        message: 'success',
        data: {
            shortId: result.id,
        },
    });
});
exports.createShortUrl = createShortUrl;
