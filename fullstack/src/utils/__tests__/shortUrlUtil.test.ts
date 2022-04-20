
import { ShortUrlUtils } from '../shortUrlUtils'
const shortUrlUtils = new ShortUrlUtils();
describe("test shortUrlUtil", () => {
    it("test generateShortHash",()=>{
        const hash = shortUrlUtils.generateShortHash("http://www.baidu.com")
        const hash2 = shortUrlUtils.generateShortHash("http://www.baidu.com")
        const hash3 = shortUrlUtils.generateShortHash("http://www.zhihu.com")
        const hash4 = shortUrlUtils.generateShortHash("")



        expect(hash.length).toBe(6);
        expect(hash2.length).toBe(6);
        expect(hash3.length).toBe(6);
        expect(hash4).toBe(null);
        
        expect(hash).toEqual(hash2)
        expect(hash).not.toEqual(hash3)

    })

    it("test generateShortUrl",()=>{
        const url1 = shortUrlUtils.generateShortUrl("http://www.baidu.com/as?a=2","abcdef")
        const url2 = shortUrlUtils.generateShortUrl("http://www.baidu.com/222/23233","abcdef")
        const url3 = shortUrlUtils.generateShortUrl("http://www.zhihu.com","abcdef")
        const url4 = shortUrlUtils.generateShortUrl("http://www.zhihu.com","")
        const url5 = shortUrlUtils.generateShortUrl("","")


        expect(url1).toEqual("http://www.baidu.com/abcdef")
        expect(url2).toEqual("http://www.baidu.com/abcdef")
        expect(url3).toEqual("http://www.zhihu.com/abcdef")
        expect(url4).toEqual("http://www.zhihu.com/")
        expect(url5).toBe(null)

    })
})