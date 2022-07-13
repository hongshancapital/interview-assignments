import ShortUrlRepo from "../database/repository/ShortUrlRepo"
// key 的长度
const KEY_LENGTH = 8;
// 生成随机字符串所基于的字符串集
const KEY_DICTIONARY = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

export default class KeyGenerateService {

    public static shortenUrl(url : string): string
    {
        const key = this.generateKey();
        ShortUrlRepo.Add(key, url);
        return key;
    }
    public static findUrlByKey(key : string): string | undefined  
    {
        return ShortUrlRepo.findByKey(key);
    }
    private static generateKey(): string {
        let exist = false;
        let key = "";
        //如果生成的key 已经存在，则重新生成。
        do{
            key = this.generateKeyString();
            exist = ShortUrlRepo.findByKey(key)!==undefined;
        }while (exist) 
        return key;
    }
    
    //生成随机字符串
    private static generateKeyString(): string {
        const range = KEY_DICTIONARY.length - 1;
        let length = KEY_LENGTH;
        let key = "";
        while (length > 0) {
            //生成0-字典长度的随机数
            const randomNum = Math.trunc(Math.random() * range);
            key += KEY_DICTIONARY[randomNum];
            length--;
        }
        return key;
    }
}