import { closeDataSource, loadDataSource } from 'src/utils';
import ShortUrlController from './short-url';

const mockLongUrl = 'https://www.google.com?key=123';

describe('Short URL controller', () => {
    beforeEach(async () => {
        await loadDataSource();
    });

    afterEach(async () => {
        await closeDataSource();
    });

    it('Should create a shortUrl', async () => {
        const expiredAt = Date.now() + 1000 * 60;
        const data = await ShortUrlController.create(mockLongUrl, expiredAt);

        expect(data.longUrl).toEqual(mockLongUrl);
    });
});
