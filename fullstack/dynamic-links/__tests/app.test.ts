import request from 'supertest';
import app from '../src/app';

let mockedExp: jest.Mock;

jest.mock('../src/models/Link', () => ({
  findOne: jest.fn()
}));

describe('App Test', () => {
  beforeEach(() => {
    mockedExp = jest.fn()
      .mockImplementation();
    mockedExp.mockClear();
  });

  test('GET /api/link/resume/6KJDRQ1K should return 404', done => {
    request(app)
      .get('/api/link/resume/6KJDRQ1K')
      .expect(404, done);
  });

});
