import * as dbHandler from '../db';
import request from 'supertest';
import app from '../../src/app';

const mockUrl = 'https://en.wikipedia.org/wiki/Albert_Einstein';
const _request = request(app);

beforeAll(async () => {
  await dbHandler.connect();
});

afterEach(async () => {
  await dbHandler.clearDatabase();
});

afterAll(async () => {
  await dbHandler.closeDatabase();
});

async function shortenAndResume() {
  const shortenResponse = await _request.post('/api/link/shorten')
    .send({ url: mockUrl });
  const keyword = shortenResponse.body.keyword;
  expect(keyword)
    .toMatch(/[A-Za-z0-9_-]{8}/);
  const resumeResponse = await _request.get(`/api/link/resume/${keyword}`);
  const url = resumeResponse.body.url;
  expect(url)
    .toMatch(/^(https?):\/\/[^\s$.?#].[^\s]*$/);
}

describe('Dynamic Links', () => {

  it('Shorten and Resume Once', async () => {
    await shortenAndResume();
  });

  it('Shorten and Resume Many times', async () => {
    for (let i = 0; i < 100; i++) {
      await shortenAndResume();
    }
  });

});
