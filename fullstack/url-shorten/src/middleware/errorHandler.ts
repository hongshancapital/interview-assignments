import { Request, Response, NextFunction } from "express"

import logger from "../lib/logger"

import MSG from "../model/json"

export function notFound(
    req: Request,
    res: Response,
    next: NextFunction
): void {
    const msg: MSG = {
        Code: 404,
        Text: `the inputAPI is not Found [${req.originalUrl}]`,
    }
    res.send(msg)

    logger.http(`the inputAPI is not Found [${req.originalUrl}]`)

    next()
}
