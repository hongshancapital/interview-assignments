import  * as base62 from 'base62';
import  * as murmurhash from 'murmurhash';

export class ShortUrlUtils{


    public generateShortHash(longUrl: string): string{
        if(longUrl){
            const hash = murmurhash.v3(longUrl);
            return base62.encode(hash);
        }
        return null;
    }

    // 根据短链接hash和长链接生成短链接
    public generateShortUrl(longUrl: string,hash: string ):string{
        if(longUrl){
            const longUrlObj = new URL(longUrl);
            const shortUrl = longUrlObj.origin + "/" + hash;
            return new URL(hash,shortUrl).href;
        }
        return null;
        
    }

}