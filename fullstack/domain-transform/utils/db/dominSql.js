/**
 * 域名的操作的sql语句工具类
 * @type {{queryByName: string, insert: string}}
 */
var dominSql = {
    insert:'INSERT INTO domain_info(longDomain, shortDomain, createDate, domainDescription) VALUES(?,?,?,?)',
    queryByName: 'SELECT domain_info.longDomain FROM domain_info WHERE shortDomain=?',
};
module.exports = dominSql;
