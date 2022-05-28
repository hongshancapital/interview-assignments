import { generateKeyword } from '../../src/controllers/link/shorten';
import * as dbHandler from '../db';

beforeAll(async () => {
  await dbHandler.connect();
});

afterEach(async () => {
  await dbHandler.clearDatabase();
});

afterAll(async () => {
  await dbHandler.closeDatabase();
});

describe('Link DAL', () => {

  describe('generate keyword', () => {
    it('should generate only one', async () => {
      const keyword = await generateKeyword();
      expect(keyword)
        .not
        .toBeNull();
    });
  });

});
