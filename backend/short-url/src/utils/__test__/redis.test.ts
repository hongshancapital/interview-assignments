import { getRedisKey } from '@utils/redis';

describe('redisKey test', () => {
    it('merge redis key', () => {
        const prefix = 'prefix';
        const finalKey = getRedisKey(prefix, 'key1', 'key2');
        expect(finalKey).toEqual(`${prefix}:key1:key2`);
    });

    it('merge redis key for array', () => {
        const prefix = 'prefix';
        const finalKey = getRedisKey([prefix, 'key1', 'key2']);
        expect(finalKey).toEqual(`${prefix}:key1:key2`);
    });
});
