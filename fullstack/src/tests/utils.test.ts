import {Alphabet, GENERATE_LENGTH, ShorterListGenerator} from "../utils/generate_shorter";
import chai from "chai";
import {client, GetLongNameFromRedis, MrProper, SyncShortToRedis} from "../utils/redis_setup";
import {ExpireTime} from "../server";

chai.should();

describe('testing utils package', () => {
   describe('testing generator file', () => {
      it('should generate n^m shortnames', () => {
         ShorterListGenerator().length.should.equal(Math.pow(Alphabet.length, GENERATE_LENGTH));
      });

      it('should generate no repeated shortnames', () => {
         const noRepeatedIds = [...new Set(ShorterListGenerator())];
         noRepeatedIds.length.should.equal(Math.pow(Alphabet.length, GENERATE_LENGTH));
      });
   });

   describe('testing redis file', () => {
      before((done) => {
         client.del("shortname-generator");
         done();
      });

      it('should sync generated shorter to redis', async () => {
         await SyncShortToRedis();
         const syncCount = await client.lLen('shortname-generator');
         syncCount.should.equal(Math.pow(Alphabet.length, GENERATE_LENGTH));
      });
   });

   describe('testing redis utlis', () => {
      it('should get shortName', async () => {
        setInterval(async () => {
            const shorter = await GetLongNameFromRedis("www.google.com");
            shorter.length.should.equal(8);
         }, (ExpireTime + 1) * 1000)
      });

      it('should recycle keys', async () => {
         await MrProper("x.xx/AAA");
         console.log("recycle test complete!");
      });
   });
});