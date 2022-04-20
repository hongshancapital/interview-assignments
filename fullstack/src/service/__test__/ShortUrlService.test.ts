import {ShortUrlService} from '../ShortUrlService'
import "reflect-metadata"
import {init} from '../../server';
let server
let app
describe("test shortUrlService", () => {
    beforeAll(async () => {
        const myApp = await init();
        app = myApp.app;
        server = myApp.server;
    })
    afterAll((done)=>{
        server.close();
        done()
    })
    it("test generateShortUrlHash",async (done)=>{
        const shortUrlService = new ShortUrlService();
        const hash =  await shortUrlService.generateShortUrlHash("http://www.baidu.com")
        expect(hash).toBe("3kb2uL")
        
    })
   
})