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
    console.log(id);
    if (!id) {
        console.log('111');
        return res.status(400).json({ data: 'id为必传字段' });
    }
    let info = (0, cache_1.cacheGet)(id); // 从缓存中取数据
    if (!info) {
        info = yield Slink.findOne({ id }).catch((err) => {
            console.log(err);
            console.log('33333');
            // return res.status(400).json({ data: err });
        });
        // .catch((err) => {
        //   console.log('333333')
        //   console.log(err);
        //   return res.status(400).json({ data: err });
        // });
        // const cacheVal = {
        //   id: info.id,
        //   url: info.url,
        // };
        // cacheSet(id, cacheVal);
    }
    res.status(200).json({
        code: 0,
        message: 'success',
        data: {
            url: info.url,
        },
    });
});
exports.getLongUrl = getLongUrl;
