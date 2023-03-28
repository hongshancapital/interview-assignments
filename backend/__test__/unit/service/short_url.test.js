"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const globals_1 = require("@jest/globals");
const short_url_1 = require("../../../src/service/short_url");
const app_1 = __importDefault(require("../../../src/app"));
const error_1 = require("../../../src/middleware/error");
(0, globals_1.describe)('shortUrlService', () => {
    let shortUrlService;
    beforeEach(async () => {
        const app = new app_1.default();
        await app.init();
        const ctx = app.createAnonymousContext();
        shortUrlService = new short_url_1.ShortUrlService(ctx);
    });
    (0, globals_1.describe)('method', () => {
        (0, globals_1.it)('should string10to62', () => {
            const count = 10006;
            (0, globals_1.expect)(shortUrlService.string10to62(count)).toEqual('2Bo');
        });
        (0, globals_1.it)('should insert and get', async () => {
            const longUrl = `https://baidu.com/t${Date.now()}`;
            // insert
            const shortUrl = await shortUrlService.insertUrl(longUrl);
            (0, globals_1.expect)(typeof shortUrl).toEqual('string');
            // insert duplicate
            const shortUrl2 = await shortUrlService.insertUrl(longUrl);
            (0, globals_1.expect)(shortUrl2).toEqual(shortUrl);
            // find
            const findLongUrl = await shortUrlService.find(shortUrl);
            (0, globals_1.expect)(findLongUrl).toBe(longUrl);
            // find not found
            await (0, globals_1.expect)(shortUrlService.find('0')).rejects.toThrow(error_1.ResourceNotFound);
        });
    });
});
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoic2hvcnRfdXJsLnRlc3QuanMiLCJzb3VyY2VSb290IjoiIiwic291cmNlcyI6WyJzaG9ydF91cmwudGVzdC50cyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiOzs7OztBQUFBLDJDQUFxRDtBQUNyRCw4REFBaUU7QUFDakUsMkRBQTJDO0FBRTNDLHlEQUFpRTtBQUVqRSxJQUFBLGtCQUFRLEVBQUMsaUJBQWlCLEVBQUUsR0FBRyxFQUFFO0lBQzdCLElBQUksZUFBZ0MsQ0FBQztJQUVyQyxVQUFVLENBQUMsS0FBSyxJQUFJLEVBQUU7UUFDbEIsTUFBTSxHQUFHLEdBQUcsSUFBSSxhQUFXLEVBQUUsQ0FBQztRQUM5QixNQUFNLEdBQUcsQ0FBQyxJQUFJLEVBQUUsQ0FBQztRQUNqQixNQUFNLEdBQUcsR0FBRyxHQUFHLENBQUMsc0JBQXNCLEVBQUUsQ0FBQztRQUN6QyxlQUFlLEdBQUcsSUFBSSwyQkFBZSxDQUFDLEdBQUcsQ0FBQyxDQUFDO0lBQy9DLENBQUMsQ0FBQyxDQUFDO0lBRUgsSUFBQSxrQkFBUSxFQUFDLFFBQVEsRUFBRSxHQUFHLEVBQUU7UUFDcEIsSUFBQSxZQUFFLEVBQUMscUJBQXFCLEVBQUUsR0FBRyxFQUFFO1lBQzNCLE1BQU0sS0FBSyxHQUFHLEtBQUssQ0FBQztZQUNwQixJQUFBLGdCQUFNLEVBQUMsZUFBZSxDQUFDLFlBQVksQ0FBQyxLQUFLLENBQUMsQ0FBQyxDQUFDLE9BQU8sQ0FBQyxLQUFLLENBQUMsQ0FBQztRQUMvRCxDQUFDLENBQUMsQ0FBQztRQUVILElBQUEsWUFBRSxFQUFDLHVCQUF1QixFQUFFLEtBQUssSUFBSSxFQUFFO1lBQ25DLE1BQU0sT0FBTyxHQUFHLHNCQUFzQixJQUFJLENBQUMsR0FBRyxFQUFFLEVBQUUsQ0FBQztZQUVuRCxTQUFTO1lBQ1QsTUFBTSxRQUFRLEdBQUcsTUFBTSxlQUFlLENBQUMsU0FBUyxDQUFDLE9BQU8sQ0FBQyxDQUFDO1lBQzFELElBQUEsZ0JBQU0sRUFBQyxPQUFPLFFBQVEsQ0FBQyxDQUFDLE9BQU8sQ0FBQyxRQUFRLENBQUMsQ0FBQztZQUUxQyxtQkFBbUI7WUFDbkIsTUFBTSxTQUFTLEdBQUcsTUFBTSxlQUFlLENBQUMsU0FBUyxDQUFDLE9BQU8sQ0FBQyxDQUFDO1lBQzNELElBQUEsZ0JBQU0sRUFBQyxTQUFTLENBQUMsQ0FBQyxPQUFPLENBQUMsUUFBUSxDQUFDLENBQUM7WUFFcEMsT0FBTztZQUNQLE1BQU0sV0FBVyxHQUFHLE1BQU0sZUFBZSxDQUFDLElBQUksQ0FBQyxRQUFRLENBQUMsQ0FBQztZQUN6RCxJQUFBLGdCQUFNLEVBQUMsV0FBVyxDQUFDLENBQUMsSUFBSSxDQUFDLE9BQU8sQ0FBQyxDQUFDO1lBRWxDLGlCQUFpQjtZQUNqQixNQUFNLElBQUEsZ0JBQU0sRUFBQyxlQUFlLENBQUMsSUFBSSxDQUFDLEdBQUcsQ0FBQyxDQUFDLENBQUMsT0FBTyxDQUFDLE9BQU8sQ0FBQyx3QkFBZ0IsQ0FBQyxDQUFDO1FBQzlFLENBQUMsQ0FBQyxDQUFDO0lBQ1AsQ0FBQyxDQUFDLENBQUM7QUFDUCxDQUFDLENBQUMsQ0FBQyJ9