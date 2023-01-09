"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.responseShortUrl = exports.generateShortUrl = void 0;
const connection_1 = __importDefault(require("../db/connection"));
const ShortUrlMapping_1 = require("../entity/ShortUrlMapping");
const generateUniqueId_1 = __importDefault(require("../utils/generateUniqueId"));
const getHashCode_1 = __importDefault(require("../utils/getHashCode"));
// 短链服务域名, 后续设置为环境变量，根据部署环境从config中取值
// 校验originalUrl是否合法
function checkUrl(url) {
    return /^(http|https):\/\/[\w\-_\u4E00-\u9FA5:/]+(\.[\w\-_\u4E00-\u9FA5]+)+([\u4E00-\u9FA5\w\-.,@?^=%&:/~+#]*[\u4E00-\u9FA5\w\-@?^=%&/~+#])?$/.test(url);
}
async function generateShortUrl(req, res) {
    // 此接口需校验身份
    const shortUrlMappingRepository = connection_1.default.getRepository(ShortUrlMapping_1.ShortUrlMapping);
    const response = {
        code: 200,
        message: '创建成功',
    };
    const postData = req.body;
    // 校验参数是否合法
    if (postData?.originalUrl && checkUrl(postData.originalUrl)) {
        // 使用hash码与原链接查询是否库中已存在相应的短链
        // 若存在则返回，不存在则创建新纪录并返回
        const originalUrlHash = (0, getHashCode_1.default)(postData.originalUrl);
        // original_url_hash 建立索引加速查询, original_url 条件防止hash碰撞，保证结果唯一
        // where original_url_hash = $originalUrlHash AND original_url = $originalUrl
        let result = await shortUrlMappingRepository.findOneBy({
            originalUrlHash,
            originalUrl: postData.originalUrl,
        });
        let isNew = 0;
        if (result === null) {
            isNew = 1;
            let shortUrlCode = (0, generateUniqueId_1.default)();
            while (await shortUrlMappingRepository.findOneBy({
                shortUrlCode,
            }) !== null) {
                shortUrlCode = (0, generateUniqueId_1.default)();
            }
            result = await shortUrlMappingRepository.save(shortUrlMappingRepository.create({
                shortUrlCode,
                originalUrl: postData.originalUrl,
                originalUrlHash: (0, getHashCode_1.default)(postData.originalUrl),
            }));
        }
        response.data = {
            shortUrlCode: result.shortUrlCode,
            isNew,
        };
    }
    else {
        response.code = 10001;
        response.message = '创建失败,请输入正确的url地址';
    }
    res.json(response);
}
exports.generateShortUrl = generateShortUrl;
async function responseShortUrl(req, res) {
    const { code } = req.query;
    const response = {
        code: 200,
        message: '查询成功',
    };
    if (typeof code === 'string' && code.length === 8) {
        const result = await connection_1.default.getRepository(ShortUrlMapping_1.ShortUrlMapping).findOneBy({
            shortUrlCode: code,
        });
        if (result !== null) {
            response.data = {
                originalUrl: result.originalUrl,
            };
        }
        else {
            response.code = 10002;
            response.message = '未查询到绑定网址';
        }
    }
    else {
        response.code = 10003;
        response.message = '网址不存在';
    }
    res.json(response);
}
exports.responseShortUrl = responseShortUrl;
