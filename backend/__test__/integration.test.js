"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const globals_1 = require("@jest/globals");
const supertest_1 = __importDefault(require("supertest"));
const app_1 = __importDefault(require("../src/app"));
(0, globals_1.describe)('shortUrlService', () => {
    let app;
    let request;
    beforeEach(async () => {
        app = new app_1.default();
        await app.init();
        request = (0, supertest_1.default)(app.callback());
    });
    (0, globals_1.describe)('root', () => {
        (0, globals_1.it)('get notfound', async () => {
            const result = await request.get('/find?short_url=0');
            (0, globals_1.expect)(result.statusCode).toBe(404);
            (0, globals_1.expect)(result.body.meta.message).toEqual('not found');
        });
        (0, globals_1.it)('insert and get', async () => {
            const longUrl = `https://baidu.com/t${Date.now()}`;
            const insertRs = await request.post('/insert').send({
                url: longUrl
            });
            const shortUrl = insertRs.body.data.short_url;
            (0, globals_1.expect)(insertRs.statusCode).toBe(200);
            (0, globals_1.expect)(typeof shortUrl).toEqual('string');
            const findRs = await request.get('/find?short_url=' + encodeURIComponent(shortUrl));
            (0, globals_1.expect)(findRs.statusCode).toBe(200);
            (0, globals_1.expect)(findRs.body.data.long_url).toEqual(longUrl);
        });
    });
});
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiaW50ZWdyYXRpb24udGVzdC5qcyIsInNvdXJjZVJvb3QiOiIiLCJzb3VyY2VzIjpbImludGVncmF0aW9uLnRlc3QudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6Ijs7Ozs7QUFBQSwyQ0FBcUQ7QUFDckQsMERBQWtDO0FBQ2xDLHFEQUFxQztBQUVyQyxJQUFBLGtCQUFRLEVBQUMsaUJBQWlCLEVBQUUsR0FBRyxFQUFFO0lBQzdCLElBQUksR0FBZ0IsQ0FBQztJQUNyQixJQUFJLE9BQTRDLENBQUE7SUFDaEQsVUFBVSxDQUFDLEtBQUssSUFBSSxFQUFFO1FBQ2xCLEdBQUcsR0FBRyxJQUFJLGFBQVcsRUFBRSxDQUFDO1FBQ3hCLE1BQU0sR0FBRyxDQUFDLElBQUksRUFBRSxDQUFDO1FBQ2pCLE9BQU8sR0FBRyxJQUFBLG1CQUFTLEVBQUMsR0FBRyxDQUFDLFFBQVEsRUFBRSxDQUFDLENBQUM7SUFDeEMsQ0FBQyxDQUFDLENBQUM7SUFFSCxJQUFBLGtCQUFRLEVBQUMsTUFBTSxFQUFFLEdBQUcsRUFBRTtRQUNsQixJQUFBLFlBQUUsRUFBQyxjQUFjLEVBQUUsS0FBSyxJQUFJLEVBQUU7WUFDMUIsTUFBTSxNQUFNLEdBQUcsTUFBTSxPQUFPLENBQUMsR0FBRyxDQUFDLG1CQUFtQixDQUFDLENBQUM7WUFDdEQsSUFBQSxnQkFBTSxFQUFDLE1BQU0sQ0FBQyxVQUFVLENBQUMsQ0FBQyxJQUFJLENBQUMsR0FBRyxDQUFDLENBQUM7WUFDcEMsSUFBQSxnQkFBTSxFQUFDLE1BQU0sQ0FBQyxJQUFJLENBQUMsSUFBSSxDQUFDLE9BQU8sQ0FBQyxDQUFDLE9BQU8sQ0FBQyxXQUFXLENBQUMsQ0FBQztRQUMxRCxDQUFDLENBQUMsQ0FBQztRQUVILElBQUEsWUFBRSxFQUFDLGdCQUFnQixFQUFFLEtBQUssSUFBSSxFQUFFO1lBQzVCLE1BQU0sT0FBTyxHQUFHLHNCQUFzQixJQUFJLENBQUMsR0FBRyxFQUFFLEVBQUUsQ0FBQztZQUVuRCxNQUFNLFFBQVEsR0FBRyxNQUFNLE9BQU8sQ0FBQyxJQUFJLENBQUMsU0FBUyxDQUFDLENBQUMsSUFBSSxDQUFDO2dCQUNoRCxHQUFHLEVBQUUsT0FBTzthQUNmLENBQUMsQ0FBQztZQUNILE1BQU0sUUFBUSxHQUFHLFFBQVEsQ0FBQyxJQUFJLENBQUMsSUFBSSxDQUFDLFNBQVMsQ0FBQztZQUU5QyxJQUFBLGdCQUFNLEVBQUMsUUFBUSxDQUFDLFVBQVUsQ0FBQyxDQUFDLElBQUksQ0FBQyxHQUFHLENBQUMsQ0FBQztZQUN0QyxJQUFBLGdCQUFNLEVBQUMsT0FBTyxRQUFRLENBQUMsQ0FBQyxPQUFPLENBQUMsUUFBUSxDQUFDLENBQUM7WUFFMUMsTUFBTSxNQUFNLEdBQUcsTUFBTSxPQUFPLENBQUMsR0FBRyxDQUFDLGtCQUFrQixHQUFHLGtCQUFrQixDQUFDLFFBQVEsQ0FBQyxDQUFDLENBQUM7WUFDcEYsSUFBQSxnQkFBTSxFQUFDLE1BQU0sQ0FBQyxVQUFVLENBQUMsQ0FBQyxJQUFJLENBQUMsR0FBRyxDQUFDLENBQUM7WUFDcEMsSUFBQSxnQkFBTSxFQUFDLE1BQU0sQ0FBQyxJQUFJLENBQUMsSUFBSSxDQUFDLFFBQVEsQ0FBQyxDQUFDLE9BQU8sQ0FBQyxPQUFPLENBQUMsQ0FBQztRQUN2RCxDQUFDLENBQUMsQ0FBQTtJQUNOLENBQUMsQ0FBQyxDQUFDO0FBQ1AsQ0FBQyxDQUFDLENBQUMifQ==