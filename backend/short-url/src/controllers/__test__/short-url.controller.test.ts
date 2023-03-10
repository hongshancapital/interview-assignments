import { closeDB, closeRedis, loadDB } from '@utils';
import ShortUrlController from '@controllers/short-url.controller';

const mockLongUrl = 'https://www.google.com?key=124';

describe('Short URL controller', () => {
    beforeEach(async () => {
        await loadDB();
    });

    afterEach(async () => {
        await closeDB();
        await closeRedis();
    });

    it('Should create a shortUrl', async () => {
        const mReq: any = { body: { longUrl: mockLongUrl, expiredAt: Date.now() + 1000 * 60 } };
        const mRes: any = {};
        const mNext = jest.fn();
        const shortUrlController = new ShortUrlController();
        await shortUrlController.create(mReq, mRes, mNext);

        expect(mRes.status).toEqual(201);
    });
});
