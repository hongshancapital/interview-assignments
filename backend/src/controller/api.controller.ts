import { Request, Response } from "express";
import ShortLink from '../model/shortLink';

/**
 * 根据长网址获取短网址
 * @author thz
 * @param {*} url
 * @return {*}
 */
export const findShortUrl = async(req: Request, res: Response) => {
    const { url } = req.params;
    // @ts-ignore
    const shortLink: ShortLink<any, any> = await ShortLink.findAll({
        where: {originalUrl: url}
    });
    // 判断是否有短链接，没有就生成并保存
    if(shortLink && !shortLink.length) {
        // @ts-ignore
        const obj: ShortLink<any, any> = await ShortLink.create({ originalUrl: url });
        // 生成短链接
        const result = EncodeStr(obj.id);
        // 注：需要过期时间也可加入过期时间
        const updateObj = await ShortLink.update({ shortUrl: result.data }, {
            where: {
                id: obj.id
            }
        });
        if (updateObj.length) {
            res.send({
                code: 200,
                data: result.data
            });
        } else {
            res.send({
                code: 201,
                msg: '失败'
            });
        }
    }
    res.send({
        code: 200,
        data: shortLink[0].shortUrl
    });
}

/**
 * 根据短网址获取原网址
 * @author thz
 * @param {*} url
 * @return {*}
 */
export const findLongUrl = async(req: Request, res: Response) => {
    const { url } = req.params;
    // @ts-ignore
    const data: ShortLink<any, any> = await ShortLink.findOne({
        where: {shortUrl: url}
    });
    if(!data) {
        res.send({
            code: 201,
            msg: `数据不存在`
        })
    }
    res.send({
        code: 200,
        msg: data.originalUrl
    })
}

function EncodeStr(number: string) {
    if(!parseInt(number)){
        let codeMsg = "请传入数字类型";
        return {
            code: 201,
            msg: codeMsg
        }
    }
    let randomInsertStr = '';
    let chars = '0123456789abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ'.split(''),
        radix = chars.length,
        qutient = +number,
        arr = [];
    let mod;
    do {
        mod = qutient % radix;
        qutient = (qutient - mod) / radix;
        arr.unshift(chars[mod]);
    } while (qutient);
    let codeStr = arr.join('');
    codeStr = codeStr.length < 6 ?  (codeStr + randomWord(false,6-codeStr.length,0,[randomInsertStr])):codeStr;
    return {
        code: 200,
        // data: CustomConfig.domain + codeStr
        data: codeStr
    }
}

let randomWord = (randomFlag: boolean, min: number, max: number, ruledOutStr: string[])=>{
    let str = '',
        range = min,
        pos: number = 0,
        arr = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
    if(ruledOutStr){
        ruledOutStr.map(item=>{
            arr = arr.join("").split(item).join('').split('')
        })
    }

    // 随机产生
    if(randomFlag){
        range = Math.round(Math.random() * (max-min)) + min;
    }
    for(let i = 0; i < range; i++){
        pos = Math.round(Math.random() * (arr.length-1));
        str += arr[pos];
    }
    return str;
};

/**
 * 获取短链接后缀
 * @author thz
 * @param {*} url
 * @return {*}
 */
function getShortPath (url: string) {
    const arr = url.split('/');
    return arr[arr.length - 1];
}

