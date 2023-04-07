import { nanoid } from 'nanoid';
import { getDiffShortCode } from '../src/util';
jest.mock('nanoid');
const mockNanoid = jest.mocked(nanoid);

describe('util', () => {
    beforeEach(() => {
        mockNanoid.mockClear();
    });
    it('getDiffShortCode-first diff', () => {
        mockNanoid.mockReturnValueOnce('test1');
        const shortCode = 'test';
        const result = getDiffShortCode(shortCode);
        expect(result != shortCode).toBeTruthy();
        expect(mockNanoid).toBeCalledTimes(1);
    });
    it('getDiffShortCode-second diff', () => {
        expect(mockNanoid).toBeCalledTimes(0);
        mockNanoid.mockReturnValueOnce('test').mockReturnValueOnce('test2');
        const shortCode = 'test';
        const result = getDiffShortCode(shortCode);
        expect(result != shortCode).toBeTruthy();
        expect(mockNanoid).toBeCalledTimes(2);
    });
});
