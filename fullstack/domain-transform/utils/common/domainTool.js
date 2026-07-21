/**
 * 域名校验规则的工具类
 * @type {{checkDomian: (function(*=): boolean)}}
 */
var domainTool = {
    /**
     * 验证域名是否合法
     * @param domainName
     * @returns {boolean}
     */
    checkDomian: function (domainName) {
        const domainExp = /[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\.?/g;
        return domainExp.test(domainName);
    }
};
module.exports = domainTool;
