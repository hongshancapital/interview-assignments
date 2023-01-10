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
const logger_1 = __importDefault(require("../utils/logger"));
// 短链服务域名, 后续设置为环境变量，根据部署环境从config中取值
// 校验originalUrl是否合法
function checkUrl(url) {
    return /^(http|https):\/\/[\w\-_\u4E00-\u9FA5:/]+(\.[\w\-_\u4E00-\u9FA5]+)+([\u4E00-\u9FA5\w\-.,@?^=%&:/~+#]*[\u4E00-\u9FA5\w\-@?^=%&/~+#])?$/.test(url);
}
const ERROR_CODE = {
    SUCCESS: {
        code: 200,
        message: '成功',
    },
    PARAMS_ERROR: {
        code: 501,
        message: '参数错误',
    },
    BIZ_ERROR: {
        code: 505,
        message: '业务异常',
    },
};
async function generateShortUrl(req, res) {
    // 此接口需校验身份
    const shortUrlMappingRepository = connection_1.default.getRepository(ShortUrlMapping_1.ShortUrlMapping);
    let response = {
        ...ERROR_CODE.SUCCESS,
    };
    const postData = req.body;
    logger_1.default.info(`[generateShortUrl]入参为: ${JSON.stringify(postData)}`);
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
            logger_1.default.info(`[generateShortUrl]创建新记录成功, 表中数据为: ${JSON.stringify(result)}`);
        }
        else {
            logger_1.default.info(`[generateShortUrl]表中已存在该原链接数据, originalUrl为: ${JSON.stringify(postData.originalUrl)}, 表中数据为: ${JSON.stringify(result)}`);
        }
        response.data = {
            shortUrlCode: result.shortUrlCode,
            isNew,
        };
    }
    else {
        logger_1.default.info(`[generateShortUrl]参数校验失败, originalUrl为: ${JSON.stringify(postData.originalUrl)}`);
        response = {
            ...ERROR_CODE.PARAMS_ERROR,
            message: '创建失败,请输入正确的url地址',
        };
    }
    logger_1.default.info(`[generateShortUrl]响应数据为: ${JSON.stringify(response)}`);
    res.json(response);
}
exports.generateShortUrl = generateShortUrl;
async function responseShortUrl(req, res) {
    const { code } = req.query;
    let response = {
        ...ERROR_CODE.SUCCESS,
    };
    if (typeof code === 'string' && code.length === 8) {
        const result = await connection_1.default.getRepository(ShortUrlMapping_1.ShortUrlMapping).findOneBy({
            shortUrlCode: code,
        });
        if (result !== null) {
            response.data = {
                originalUrl: result.originalUrl,
            };
            logger_1.default.info(`[responseShortUrl]code对应的记录为: ${JSON.stringify(result)}`);
        }
        else {
            logger_1.default.info(`[responseShortUrl]未查询到code对应的记录, code为: ${code}`);
            response = {
                ...ERROR_CODE.BIZ_ERROR,
                message: '未查询到绑定网址',
            };
        }
    }
    else {
        logger_1.default.info(`[responseShortUrl]参数校验失败, code为: ${JSON.stringify(code)}`);
        response = {
            ...ERROR_CODE.PARAMS_ERROR,
        };
    }
    logger_1.default.info(`[responseShortUrl]响应结果为: ${JSON.stringify(response)}`);
    res.json(response);
}
exports.responseShortUrl = responseShortUrl;
