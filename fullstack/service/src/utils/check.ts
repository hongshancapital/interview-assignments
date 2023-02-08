import { errorType } from '@/configs';

type CheckType = StringConstructor | ArrayConstructor | ObjectConstructor | NumberConstructor | BooleanConstructor

interface CheckRuleType {
    type: CheckType,
    notEmpty?: boolean
    required?: boolean
    msg?: string
    error?: string
}
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
const dealCheckType = (param: CheckType) => typeof param() !== 'object' ? typeof param() : Array.isArray(param()) ? 'array' : typeof param();
const dealParamType = (param: unknown) => typeof param !== 'object' ? typeof param : Array.isArray(param) ? 'array' : typeof param;

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function check(param: any, type: CheckType, allowedEmpty?: boolean, error?: string, msg?: string): void;
// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function check(param: any, type: { [x: string]: CheckType }): void;
// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function check(param: any, type: { [x: string]: CheckRuleType }): void;

/**
 * 检查数据的合法性
 */
// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function check(param: any, type: CheckType | { [x: string]: CheckType | CheckRuleType }, allowedEmpty = true, error = errorType.INVALID_ARGUMENTS, msg?: string): void {
    if (param === null || param === undefined) {
        throw new Exception(msg || `Invalid arguments: ${param}`, error);
    }

    if (type === String || type === Array || type === Object || type === Number || type === Boolean) {
        /**
         * example:
         * check(x, String, false);
         */

        if (param.constructor !== type) {
            throw new Exception(msg || `Invalid arguments: require ${dealCheckType(type)}, but get ${JSON.stringify(param)} is ${dealParamType(param)}`, error);
        }

        if (!allowedEmpty) {
            if (type === String && typeof param === 'string' && param.trim() === '') {
                throw new Exception(msg || 'Invalid arguments: not allowed empty string.', error);
            }

            if (type === Object && Object.keys(param).length === 0) {
                throw new Exception(msg || 'Invalid arguments: not allowed empty object.', error);
            }

            if (type === Array && Array.isArray(param) && param.length === 0) {
                throw new Exception(msg || 'Invalid arguments: not allowed empty array.', error);
            }

            if (type === Number && param === 0) {
                throw new Exception(msg || 'Invalid arguments: not allowed 0.', error);
            }
        }
    } else {
        if (param.constructor !== Object) {
            throw new Exception(`Invalid arguments: require object, but get ${dealParamType(param)}`, errorType.INVALID_ARGUMENTS);
        }

        for (const key in type) {
            if (type[key] === String || type[key] === Array || type[key] === Object || type[key] === Number || type[key] === Boolean) {
                /**
                 * example:
                 *  check(x, {
                        key1: String
                    });
                 */

                if (typeof param[key] !== 'undefined' && param[key].constructor !== type[key]) {
                    throw new Exception(`Invalid arguments: require object key "${key}" is ${dealCheckType(type[key])}, but get ${JSON.stringify(param[key])} is ${dealParamType(param[key])}`, errorType.INVALID_ARGUMENTS);
                }
            } else {
                /**
                 * example:
                 *  check(x, {
                        key1: { type: String, required: true, notEmpty: true, msg: '', error: 'INVALID_ARGUMENTS' }
                    });
                 */
                const rule = type[key] as CheckRuleType;

                if (rule.type === String || rule.type === Array || rule.type === Object || rule.type === Number || rule.type === Boolean) {
                    if (rule.required === true && (param[key] === null || param[key] === undefined)) {
                        throw new Exception(rule.msg || `Invalid arguments: object key "${key}" is required.`, rule.error || errorType.INVALID_ARGUMENTS);
                    }

                    if (param[key] !== null && param[key] !== undefined) {
                        if (param[key].constructor !== rule.type) {
                            throw new Exception(rule.msg || `Invalid arguments: require object key "${key}" is ${dealCheckType(rule.type)}, but get ${JSON.stringify(param[key])} is ${dealParamType(param[key])}`, rule.error || errorType.INVALID_ARGUMENTS);
                        }

                        if (rule.notEmpty === true) {
                            if (rule.type === String && typeof param[key] === 'string' && param[key].trim() === '') {
                                throw new Exception(rule.msg || `Invalid arguments: object key "${key}" not allowed empty string.`, rule.error || errorType.INVALID_ARGUMENTS);
                            }

                            if (rule.type === Object && Object.keys(param[key]).length === 0) {
                                throw new Exception(rule.msg || `Invalid arguments: object key "${key}" not allowed empty object.`, rule.error || errorType.INVALID_ARGUMENTS);
                            }

                            if (rule.type === Array && Array.isArray(param[key]) && param[key].length === 0) {
                                throw new Exception(rule.msg || `Invalid arguments: object key "${key}" not allowed empty array.`, rule.error || errorType.INVALID_ARGUMENTS);
                            }

                            if (rule.type === Number && param[key] === 0) {
                                throw new Exception(rule.msg || `Invalid arguments: object key "${key}" not allowed 0.`, rule.error || errorType.INVALID_ARGUMENTS);
                            }
                        }
                    }
                }
            }
        }
    }
}
