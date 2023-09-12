const { Domain } = require('../connection/connect.ts');
const { tinyDomainName, formatUrl } = require('../unitil/unitilTool.ts');

interface resqonseDataType {
    code: number;
    message: string;
    error?: string;
    data?: object;
}

class resultModel implements resqonseDataType {
    code: number
    message: string
    error?: any
    data?: object
    constructor(code: number, message: string, data?: object, error?: any) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.error = error;
    }
}

async function storeShortDomainName(longName: string): Promise<resqonseDataType> {
    if (!longName) {
        return new resultModel(400, '没有传递longName参数');
    }
    const match = /^((ht|f)tps?):\/\/[\w\-]+(\.[\w\-]+)+([\w\-\.,@?^=%&:\/~\+#]*[\w\-\@?^=%&\/~\+#])?$/;
    const flag = match.test(longName);
    if (!flag) {
        return new resultModel(400, '传递参数不符合url标准');
    }
    const isExistLong = await Domain.findOne({ longName: longName });
    if (isExistLong) {
        return new resultModel(200, '获取短域名成功', formatUrl(isExistLong.shortName));
    } else {
        const shortNames = tinyDomainName(longName);
        try {
            let index = 0;
            while (index < 4) {
                const isExistShort = await Domain.findOne({ shortName: shortNames[index] });
                if (!isExistShort) {
                    break;
                } else {
                    index++;
                }
            }
            if (index === 4) {
                return new resultModel(500, '获取短域名失败，长域名对应短域名重复');
            }
            await Domain.create({ longName: longName, shortName: shortNames[index] });
            return new resultModel(200, '获取短域名成功', formatUrl(shortNames[index]));
        } catch (err) {
            return new resultModel(500, '获取短域名失败', undefined, err);
        }
    }
}
async function getLongDomainName(shortName: string): Promise<resqonseDataType> {
    if (!shortName) {
        return new resultModel(400, '没有传递shortName参数');
    }
    try {
        const longTinyNameExt = await Domain.findOne({
            where: {
                shortName: shortName
            }
        });
        if (!longTinyNameExt) {
            return new resultModel(400, '短域名无效，无法获取长域名');
        } else {
            return new resultModel(200, '获取长域名成功', longTinyNameExt.longName);
        }
    } catch (err) {
        return new resultModel(500, '获取长域名失败', undefined, err);
    }
}
module.exports = {
    storeShortDomainName,
    getLongDomainName

};