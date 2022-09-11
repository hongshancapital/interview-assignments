// 引入mysql
var mysql = require('mysql');
// 引入mysql连接配置
var mysqlConfig = require('./config/mysqlConfig');
// 引入连接池配置
var poolExtend = require('./config/poolExtend');
// 引入SQL模块
var dominSql = require('./dominSql');
// 引入json模块
var returnData = require('../common/returnData');
// 使用连接池，提升性能
var pool = mysql.createPool(poolExtend({}, mysqlConfig));
// 域名验证规则
var domainTool = require('../common/domainTool')
// 长域名转短域名
var transformTool = require('../common/transformUtil')

/**
 * 域名存储和读取的数据库操作类
 * @type {{add: domainData.add, query: domainData.query}}
 */
var domainData = {
    /**
     * 接受长域名信息，返回短域名信息
     * @param req
     * @param res
     * @param next
     */
    add: function(req, res, next) {
        var param = req.body;
        var longDomain = param.longDomain;
        if (!domainTool.checkDomian(longDomain)) {
            returnData(res, {result: "checkError", data: "域名格式错误"});
        } else {
            var shortDomain = transformTool.toShortDomain(longDomain)
            pool.getConnection(function(err, connection) {
                connection.query(dominSql.queryByName, shortDomain, function(err, result) {
                    if (result.length > 0) {
                        returnData(res, {result: "sameDomain", data: "该域名已存在"});
                        // 释放连接
                        connection.release();
                    } else {
                        connection.query(dominSql.insert, [longDomain, shortDomain, new Date(), param.domainDescription], function(err, result) {
                            if (result) {
                                var _result = result;
                                result = {
                                    result: 'add',
                                    data: {shortDomain: shortDomain}
                                }
                            } else {
                                result = undefined;
                            }
                            // 以json形式，把操作结果返回给前台页面
                            returnData(res, result);
                            // 释放连接
                            connection.release();
                        });
                    }
                });
            });
        }
    },
    /**
     * 接受短域名信息，返回长域名信息
     * @param req
     * @param res
     * @param next
     */
    query: function(req, res, next) {
        var shortDomain = req.params.domainName;
        if (!domainTool.checkDomian(shortDomain)) {
            returnData(res, {result: "checkError", data: "域名格式错误"});
        } else {
            if (shortDomain.length > 8) {
                returnData(res, {result: "lengthError", data: "短域名长度超出限制，最多为8个字符"});
            } else {
                pool.getConnection(function(err, connection) {
                    connection.query(dominSql.queryByName, shortDomain, function(err, result) {
                        if (result.length > 0) {
                            var _result = result;
                            result = {
                                result: 'select',
                                data: _result
                            }
                        } else {
                            result = {
                                result: 'noData',
                                data: "没有找到对应的域名"
                            }
                        }
                        returnData(res, result);
                        connection.release();
                    });
                });
            }
        }
    }
};
module.exports = domainData;
