import ShortUrlRepo from "../database/repository/ShortUrlRepo"
// 生成key 基于的字符串集
const Max_Key_Length = 8;
const Key_Dictionary = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

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
        const range = Key_Dictionary.length - 1;
        let length = Max_Key_Length;
        let key = "";
        while (length > 0) {
            let randomNum = Math.trunc(Math.random() * range);
            key += Key_Dictionary[randomNum];
            length--;
        }
        return key;
    }
}