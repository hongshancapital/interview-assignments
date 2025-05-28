import chai, {expect} from 'chai';
import {
   ClearDb,
   GetDbByLongDomain,
   GetDbByShortDomain,
   GetMainFrameLastShort,
   SaveMapToDb
} from "../../utils/postgres_setup";
import {BASE_URL, GENERATE_LENGTH, MAINFRAME_CODE} from "../../server";

chai.should();

describe('postgres_setup file test', () => {
   before(async () => {
      await ClearDb();
   })

   it('should get first shorter', async () => {
      const ret = await GetMainFrameLastShort();
      expect(ret).not.be.undefined;
      ret?.should.equal(MAINFRAME_CODE + "AAAAAAA");
   });


   it('should get empty object', async () => {
      const ret = await GetDbByLongDomain("www.google.com");
      expect(ret).be.undefined;
   });

   it('should get object by long domain', async () => {
      const map = {
         longDomain: "www.google.com",
         shortDomain: BASE_URL + MAINFRAME_CODE + "AAAAAAA"
      };
      await SaveMapToDb(map);
      const ret = await GetDbByLongDomain(map.longDomain);
      expect(ret).not.be.undefined;
      ret?.should.eql(map);
   });

   it('should get object by short domain', async () => {
      const map = {
         longDomain: "www.google.com",
         shortDomain: BASE_URL + MAINFRAME_CODE + "AAAAAAA"
      };
      const ret = await GetDbByShortDomain(map.shortDomain.substring(BASE_URL.length));
      expect(ret).not.be.undefined;
      ret?.should.eql(map);
   });

   it('should get second shorter', async () => {
      const ret = await GetMainFrameLastShort();
      ret.length.should.equal(GENERATE_LENGTH);
      ret.should.equal(MAINFRAME_CODE + "AAAAAAA");
   });

});
