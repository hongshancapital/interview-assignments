"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const globals_1 = require("@jest/globals");
const app_1 = __importDefault(require("../../../src/app"));
const short_url_1 = __importDefault(require("../../../src/controller/short_url"));
const error_1 = require("../../../src/middleware/error");
(0, globals_1.describe)('shortUrlController', () => {
    let shortUrlController;
    beforeEach(async () => {
        const app = new app_1.default();
        const ctx = app.createAnonymousContext();
        shortUrlController = new short_url_1.default(ctx);
    });
    (0, globals_1.describe)('method', () => {
        (0, globals_1.it)('should insert url', async () => {
            shortUrlController.ctx.request.body = {
                url: 'https://baidu.com/xxxxxxx'
            };
            const shortUrl = 'https://x.cn/a';
            jest.spyOn(shortUrlController.shortUrlService, 'insertUrl')
                .mockImplementation(async () => Promise.resolve(shortUrl));
            const result = await shortUrlController.insert();
            (0, globals_1.expect)(result.short_url).toBe(shortUrl);
        });
        (0, globals_1.it)('should find url', async () => {
            jest.spyOn(shortUrlController.shortUrlService, 'find')
                .mockImplementation(async () => Promise.resolve(longUrl));
            shortUrlController.ctx.request.query = {
                short_url: 'https://x.cn/a'
            };
            const longUrl = 'https://baidu.com/xxxxxxx';
            const result = await shortUrlController.find();
            (0, globals_1.expect)(result.long_url).toBe(longUrl);
        });
        (0, globals_1.it)('should handle invalid url', async () => {
            shortUrlController.ctx.request.body = {
                url: '!@*'
            };
            await (0, globals_1.expect)(shortUrlController.insert()).rejects.toThrow(error_1.ParamInvalid);
            shortUrlController.ctx.request.query = {
                short_url: ''
            };
            await (0, globals_1.expect)(shortUrlController.find()).rejects.toThrow(error_1.ParamInvalid);
        });
    });
});
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoic2hvcnRfdXJsLnRlc3QuanMiLCJzb3VyY2VSb290IjoiIiwic291cmNlcyI6WyJzaG9ydF91cmwudGVzdC50cyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiOzs7OztBQUFBLDJDQUFxRDtBQUVyRCwyREFBMkM7QUFFM0Msa0ZBQW1FO0FBQ25FLHlEQUE2RDtBQUU3RCxJQUFBLGtCQUFRLEVBQUMsb0JBQW9CLEVBQUUsR0FBRyxFQUFFO0lBQ2hDLElBQUksa0JBQXNDLENBQUM7SUFFM0MsVUFBVSxDQUFDLEtBQUssSUFBSSxFQUFFO1FBQ2xCLE1BQU0sR0FBRyxHQUFHLElBQUksYUFBVyxFQUFFLENBQUM7UUFDOUIsTUFBTSxHQUFHLEdBQUcsR0FBRyxDQUFDLHNCQUFzQixFQUFFLENBQUM7UUFDekMsa0JBQWtCLEdBQUcsSUFBSSxtQkFBa0IsQ0FBQyxHQUFHLENBQUMsQ0FBQztJQUNyRCxDQUFDLENBQUMsQ0FBQztJQUVILElBQUEsa0JBQVEsRUFBQyxRQUFRLEVBQUUsR0FBRyxFQUFFO1FBQ3BCLElBQUEsWUFBRSxFQUFDLG1CQUFtQixFQUFFLEtBQUssSUFBSSxFQUFFO1lBQy9CLGtCQUFrQixDQUFDLEdBQUcsQ0FBQyxPQUFPLENBQUMsSUFBSSxHQUFHO2dCQUNsQyxHQUFHLEVBQUUsMkJBQTJCO2FBQ25DLENBQUM7WUFFRixNQUFNLFFBQVEsR0FBRyxnQkFBZ0IsQ0FBQztZQUVsQyxJQUFJLENBQUMsS0FBSyxDQUFDLGtCQUFrQixDQUFDLGVBQWUsRUFBRSxXQUFXLENBQUM7aUJBQ3RELGtCQUFrQixDQUFDLEtBQUssSUFBcUIsRUFBRSxDQUFDLE9BQU8sQ0FBQyxPQUFPLENBQUMsUUFBUSxDQUFDLENBQUMsQ0FBQztZQUVoRixNQUFNLE1BQU0sR0FBRyxNQUFNLGtCQUFrQixDQUFDLE1BQU0sRUFBRSxDQUFDO1lBQ2pELElBQUEsZ0JBQU0sRUFBQyxNQUFNLENBQUMsU0FBUyxDQUFDLENBQUMsSUFBSSxDQUFDLFFBQVEsQ0FBQyxDQUFDO1FBQzVDLENBQUMsQ0FBQyxDQUFDO1FBR0gsSUFBQSxZQUFFLEVBQUMsaUJBQWlCLEVBQUUsS0FBSyxJQUFJLEVBQUU7WUFDN0IsSUFBSSxDQUFDLEtBQUssQ0FBQyxrQkFBa0IsQ0FBQyxlQUFlLEVBQUUsTUFBTSxDQUFDO2lCQUNqRCxrQkFBa0IsQ0FBQyxLQUFLLElBQXFCLEVBQUUsQ0FBQyxPQUFPLENBQUMsT0FBTyxDQUFDLE9BQU8sQ0FBQyxDQUFDLENBQUM7WUFFL0Usa0JBQWtCLENBQUMsR0FBRyxDQUFDLE9BQU8sQ0FBQyxLQUFLLEdBQUc7Z0JBQ25DLFNBQVMsRUFBRSxnQkFBZ0I7YUFDOUIsQ0FBQTtZQUVELE1BQU0sT0FBTyxHQUFHLDJCQUEyQixDQUFDO1lBRTVDLE1BQU0sTUFBTSxHQUFHLE1BQU0sa0JBQWtCLENBQUMsSUFBSSxFQUFFLENBQUM7WUFDL0MsSUFBQSxnQkFBTSxFQUFDLE1BQU0sQ0FBQyxRQUFRLENBQUMsQ0FBQyxJQUFJLENBQUMsT0FBTyxDQUFDLENBQUM7UUFDMUMsQ0FBQyxDQUFDLENBQUM7UUFFSCxJQUFBLFlBQUUsRUFBQywyQkFBMkIsRUFBRSxLQUFLLElBQUksRUFBRTtZQUN2QyxrQkFBa0IsQ0FBQyxHQUFHLENBQUMsT0FBTyxDQUFDLElBQUksR0FBRztnQkFDbEMsR0FBRyxFQUFFLEtBQUs7YUFDYixDQUFDO1lBQ0YsTUFBTSxJQUFBLGdCQUFNLEVBQUMsa0JBQWtCLENBQUMsTUFBTSxFQUFFLENBQUMsQ0FBQyxPQUFPLENBQUMsT0FBTyxDQUFDLG9CQUFZLENBQUMsQ0FBQztZQUV4RSxrQkFBa0IsQ0FBQyxHQUFHLENBQUMsT0FBTyxDQUFDLEtBQUssR0FBRztnQkFDbkMsU0FBUyxFQUFFLEVBQUU7YUFDaEIsQ0FBQTtZQUNELE1BQU0sSUFBQSxnQkFBTSxFQUFDLGtCQUFrQixDQUFDLElBQUksRUFBRSxDQUFDLENBQUMsT0FBTyxDQUFDLE9BQU8sQ0FBQyxvQkFBWSxDQUFDLENBQUM7UUFDMUUsQ0FBQyxDQUFDLENBQUM7SUFDUCxDQUFDLENBQUMsQ0FBQztBQUNQLENBQUMsQ0FBQyxDQUFBIn0=