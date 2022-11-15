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
exports.getLongUrl = void 0;
const mongoose_1 = require("mongoose");
const cache_1 = require("../service/cache");
const getLongUrl = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const id = req.query.id;
    if (!id) {
        return res.status(400).json({ data: 'id为必传字段' });
    }
    let info = (0, cache_1.cacheGet)(id); // 从缓存中取数据
    if (!info) {
        const SlinkModel = (0, mongoose_1.model)('Slink');
        SlinkModel.findOne({ id })
            .populate('id')
            .orFail()
            .then((doc) => {
            info = {
                id: doc.id,
                url: doc.url,
            };
            (0, cache_1.cacheSet)(id, info);
            return res.status(200).json({
                code: 0,
                message: 'success',
                data: {
                    url: info.url,
                },
            });
        })
            .catch(() => {
            return res.status(400).json({ data: '无效id' });
        });
    }
    else {
        return res.status(200).json({
            code: 0,
            message: 'success',
            data: {
                url: info.url,
            },
        });
    }
});
exports.getLongUrl = getLongUrl;
