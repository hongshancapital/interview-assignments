
/**
 * / route
 *
 * @class ShortUrlDao
 */
export class ShortUrlDao  {

    private originalUrlyMap: Map<string, string>;   // key(originalUrl),value(shortUrlID)
    private shortUrlIDMap: Map<string, string>;   // key(shortUrlID),value(originalUrl)

    constructor() {
        this.originalUrlyMap = new Map<string, string>();
        this.shortUrlIDMap = new Map<string, string>();
    }
    
    public queryOriginalUrl(shortUrl: string): string {
        if (this.shortUrlIDMap.has(shortUrl)) {
            return this.shortUrlIDMap.get(shortUrl) as string;
        }

        return "";
    }

    public queryShortUrl(originalUrl: string): string {
        if (this.originalUrlyMap.has(originalUrl)) {
            return this.originalUrlyMap.get(originalUrl) as string;
        }
        return "";
    }

    public save(shortUrl: string, originalUrl: string): boolean {

        this.originalUrlyMap.set(originalUrl, shortUrl);
        this.shortUrlIDMap.set(shortUrl, originalUrl);
        return true;
    }    
}