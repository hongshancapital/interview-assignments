import {longToShort} from './service/longtoshort';
import {validURL} from "../src/util/util";
import {shortToLong}from './service/shorttolong';

/**
 * 接收URL
 * @param url 
 * @returns 
 */
async function getShortUrl (url : string): Promise<string> {
    if(!validURL(url)){
        console.log("url is invalid.");
        return null;
    } else{
        let shortUrl = "";
        await longToShort(url).then(function(value){
            shortUrl = value;
        })
        return shortUrl;
    }
}

/**
 * 接收URL
 * @param url 
 * @returns 
 */
 async function getLongUrl (url : string): Promise<string>{
    if(!validURL(url)){
        console.log("url is invalid.");
        return null;
    } else{
        let longtUrl = "";
        await shortToLong(url).then(function(value){
            longtUrl = value;
        })
        return longtUrl;
    }
 }

 // 长域名存在，返回 https://t.cn/ABCD
let originalLong1 = "http://www.baidu.com/ssssyyysss/shhss";
getShortUrl(originalLong1).then(function(res) { 
     console.log("输出短域名: " + res);
 })
// 长域名不存在，返回""
 let originalLong2 = "http://www.baidu.com/ssssyyysss/wssss";
 getShortUrl(originalLong2).then(function(res) { 
      console.log("输出短域名: " + res);
  })
// 短域名存在,返回http://www.baidu.com/aaaaa/ccccc
let originalShort1 = "https://t.cn/FXsl";
getLongUrl(originalShort1).then(
    function(res) { 
        console.log("输出长域名: " + res);
    })
// 短域名不存在，返回""
let originalShort2 = "https://t.cn/FGHJ";
getLongUrl(originalShort2).then(
        function(res) { 
            console.log("输出长域名: " + res);
        })

