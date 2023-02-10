/**
 * 发送特定格式（如 json）结果给调用方
 */
import { InvalidCodeError } from "../errors"

/**
 * 结果对象格式
 */
export interface Result {
    code: number;
    message: string;
    data: object;
}

/**
 * 发送器
 */
export interface JsonSender {
    json: (o: Result) => void;
}

/**
 * 业务处理成功
 * @param res
 * @param data - 业务数据
 * @param code - 状态码，必须是 20X
 * @param message
 */
function jsonOK(res: JsonSender, data: object = {}, code: number = 200, message: string = 'OK') {
    if (code < 200 || code >= 300) {
        throw new InvalidCodeError('invalid success code')
    }

    res.json({ code, message, data })
}

/**
 * 业务处理失败
 * @param res 
 * @param message - 失败的具体原因，必须提供
 * @param code - 错误码，必须大于等于 300
 */
function jsonFail(res: JsonSender, message: string, code: number = 500) {
    if (code < 300) {
        throw new InvalidCodeError('invalid fail code')
    }
    
    res.json({ code, message, data: {} })
}

export default { jsonOK, jsonFail }