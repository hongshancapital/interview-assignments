// eslint-disable-next-line node/no-unpublished-import
import supertest from 'supertest';
import {app} from '../../src/server/app';

const baseUrl = '/api/v2/invalidpath';

describe('check if it is a validurl', () => {
  it('should get 404 for an invalid url', async () => {
    const response = await supertest(app).get(baseUrl).expect(404);
    expect(response.body.message).toEqual('Not Found');
  });
});
