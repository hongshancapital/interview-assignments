/**
 * 长域名和短域名的转化的工具类
 * @type {{toShortDomain: transformTool.toShortDomain}}
 */
var transformTool = {
    /**
     * 长域名转短域名，不清楚具体的转换规则，直接截取后8个字符
     * @param longDomain 规则正确的长域名
     * @return shortDomain 转换后的短域名
     */
    toShortDomain: function(longDomain) {
        var shortDomain = "";
        if (longDomain.length < 8) {
            return longDomain;
        } else {
            if (longDomain.indexOf("www.") === 0) {
                shortDomain = longDomain.substring(4, longDomain.length);
                if (shortDomain.length > 8 ) {
                    shortDomain = shortDomain.substring(shortDomain.length - 8, shortDomain.length);
                }
            } else {
                shortDomain = longDomain;
                if (shortDomain.length > 8 ) {
                    shortDomain = shortDomain.substring(shortDomain.length - 8, shortDomain.length);
                }
            }
        }
        return shortDomain;
    }
};
module.exports = transformTool;
