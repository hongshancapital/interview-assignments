
const errorType = {
    parameter: 10,
    business: 11,
};

function getCode(type: string, code: number | string): number {
    return Number(`${errorType[type]}${code}`);
}

const errorCode = {
    Parameter: getCode('parameter', '0000'), // 参数错误
    SystemInvalid: getCode('business', '0001'), // 系统权限无效
};

export default errorCode;