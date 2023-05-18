/**
 * @description API 限流器
 * @param windowMs [num] 单位毫秒
 * @param max [num] 每个 "window" 限制每个 IP 在 windowMs 时间范围内的最大请求数
 * @param standardHeaders [boolean]是否在 RateLimit-* headers 返回限流器组件的信息
 * @param legacyHeaders [boolean]
 */
import rateLimit from "express-rate-limit"
import { apiRateLimiter } from "../constants/index"

export const fetchUrlLimiter = rateLimit({
    windowMs: apiRateLimiter.fetechUrl.windowMs,
    max: apiRateLimiter.fetechUrl.max,
    standardHeaders: apiRateLimiter.fetechUrl.standardHeaders,
    legacyHeaders: apiRateLimiter.fetechUrl.legacyHeaders,
})

export const shortenLimiter = rateLimit({
    windowMs: apiRateLimiter.shorten.windowMs,
    max: apiRateLimiter.shorten.max,
    standardHeaders: apiRateLimiter.shorten.standardHeaders,
    legacyHeaders: apiRateLimiter.shorten.legacyHeaders,
})
