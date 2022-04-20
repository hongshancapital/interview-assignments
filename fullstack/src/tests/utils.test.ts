import {GetNextShorterByCurrent} from "../utils/generate_shorter";
import chai from "chai";

chai.should();
//
// describe('testing utils package', () => {
//    describe('testing generator file', () => {
//       it('should get first shorter', () => {
//          GetNextShorterByCurrent("").should.equal(MAINFRAME_CODE + "AAAAAA");
//       });
//
//       it('should get next shorter', () => {
//          GetNextShorterByCurrent(MAINFRAME_CODE + "AAAAAA").should.equal(MAINFRAME_CODE + "BAAAAA");
//       });
//    });
//
//    describe('testing redis file', () => {
//       before((done) => {
//          redisClient.del("shortname-generator");
//          done();
//       });
//
//       it('should sync generated shorter to redis', async () => {
//          await SyncShortToRedis();
//          const syncCount = await redisClient.lLen('shortname-generator');
//          syncCount.should.equal(Math.pow(Alphabet.length, GENERATE_LENGTH));
//       });
//    });
//
//    describe('testing redis utlis', () => {
//       it('should get shortName', async () => {
//         setInterval(async () => {
//             const shorter = await GetLongNameFromRedis("www.google.com");
//             shorter.length.should.equal(8);
//          }, (EXPIRE_TIME + 1) * 1000)
//       });
//
//       it('should recycle keys', async () => {
//          await MrProper("x.xx/AAA");
//          console.log("recycle test complete!");
//       });
//    });
// });