export const errorType = {
    URL_NOT_FOUND: 'URL_NOT_FOUND',
    INTERNAL_SERVER_ERROR: 'INTERNAL_SERVER_ERROR',
    BAD_REQUEST: 'BAD_REQUEST',
    FORBIDDEN: 'FORBIDDEN',
    INVALID_ARGUMENTS: 'INVALID_ARGUMENTS',
    UNAUTHORIZED: 'UNAUTHORIZED'
};

export const errorCodeMap = {
    '400': ['BAD_REQUEST', 'INVALID_ARGUMENTS'],
    '401': ['UNAUTHORIZED'],
    '403': ['FORBIDDEN'],
    '404': ['URL_NOT_FOUND'],
    '500': ['INTERNAL_SERVER_ERROR']
};

/**
 * @api {error} Error_Example 所有接口请求错误示例
 * @apiName Error_example
 * @apiGroup ERROR CODE
 * @apiVersion 1.0.0
 * @apiError {string} message 提示信息
 * @apiError {array} source 错误源追踪信息
 * @apiError {string} code 错误类型
 * @apiError {array} reason 错误信息中的变量(如有)信息
 * @apiErrorExample Error-Response:
 * {
 *   "message": "User Check Failure by userVerification",
 *   "source": [],
 *   "code": "URL_NOT_FOUND",
 *   "reason": []
 * }
*/

/**
* @api {error} Error_code API ERROR CODE错误码
* @apiName Error_code
* @apiGroup ERROR CODE
* @apiVersion 1.0.0
* @apiParam (error) {string} URL_NOT_FOUND 路由未找到
* @apiParam (error) {string} INTERNAL_SERVER_ERROR 服务器内部错误
* @apiParam (error) {string} BAD_REQUEST 请求不正确
* @apiParam (error) {string} FORBIDDEN 禁止访问
* @apiParam (error) {string} INVALID_ARGUMENTS 参数不合法
* @apiParam (error) {string} UNAUTHORIZED 没有权限
*/
