/**
 * 后端向前端返回的统一工具类
 * @param res
 * @param result
 */
var returnData = function(res, result) {
    if (typeof result === 'undefined') {
        res.json({
            code: '1',
            msg: '操作失败',
            data: result.data
        });
    } else if (result.result === 'checkError') {
        res.json({
            code: '1',
            msg: '域名格式错误',
            data: result.data
        });
    } else if (result.result === 'lengthError') {
        res.json({
            code: '1',
            msg: '短域名长度超出限制，最多为8个字符',
            data: result.data
        });
    } else if (result.result === 'sameDomain') {
        res.json({
            code: '1',
            msg: '该域名已存在',
            data: result.data
        });
    } else if (result.result === 'add') {
        res.json({
            code: '200',
            msg: '存储成功',
            data: result.data
        });
    } else if (result.result != 'undefined' && result.result === 'select') {
        res.json({
            code: '200',
            msg: '查找成功',
            data: result.data
        });
    } else if (result.result === 'noData') {
        res.json({
            code: '200',
            msg: '查找成功',
            data: result.data
        });
    }  else {
        res.json(result);
    }
};
module.exports = returnData;
