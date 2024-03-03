import chai, {expect} from 'chai';
import {DispatchShort, RedisClient, SyncGeneratorToRedis} from "../../utils/redis_setup";
import {BASE_URL, GENERATOR_KEY, MAINFRAME_CODE} from "../../server";
import {ClearDb, PostgresClient} from "../../utils/postgres_setup";

chai.should();



describe('redis file test', () => {
    before(async () => {
        await RedisClient.flushAll();
        await ClearDb();
    });

    it('should sync First Generator to redis', async () => {
        await SyncGeneratorToRedis();
        const current = await RedisClient.get(GENERATOR_KEY);
        expect(current).not.be.null;
        expect(current).to.equal(MAINFRAME_CODE + "AAAAAAA");
    });

    it('should Dispatcher shorter', async () => {
        const longDomain = "www.google.com";
        const redisCache = await DispatchShort(longDomain);
        redisCache.should.to.eql({
            longDomain: longDomain,
            shortDomain: BASE_URL + MAINFRAME_CODE + "AAAAAAA"
        });
    });
});
