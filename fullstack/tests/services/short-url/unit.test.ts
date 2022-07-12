import { mockFindByKey, mockAdd } from './mock';
import KeyGenerateService from '../../../src/services/ShortUrlService'

describe('Integrition test for short url api', () => {
  beforeEach(() => {
    mockFindByKey.mockClear();
    mockAdd.mockClear();
  });

  const endpoint = '/v1/short-url';
  const TEST_KEY = 'Test';
  const TEST_URL = 'https://stackoverflow.com';
  it('Generate key successfully.', async () => {

    const key = KeyGenerateService.shortenUrl(TEST_URL);
    expect(key && typeof key === 'string' && key.length <= 8).toBeTruthy();
    expect(mockAdd).toBeCalledTimes(1);
    expect(mockFindByKey).toBeCalledTimes(1);
  });

  it('Generate key different.', async () => {

    const key1 = KeyGenerateService.shortenUrl(TEST_URL);
    const key2 = KeyGenerateService.shortenUrl(TEST_URL);

    expect(key1).not.toEqual(key2);
    expect(mockAdd).toBeCalledTimes(2);
    expect(mockFindByKey).toBeCalledTimes(2);
  });

  it('Find correct url by key', async () => {

    const key = KeyGenerateService.shortenUrl(TEST_URL);
    const url = KeyGenerateService.findUrlByKey(key)

    expect(TEST_URL).toEqual(url);
    expect(mockAdd).toBeCalledTimes(1);
  });

  it('Find undefined by non-exist key', async () => {

    const url = KeyGenerateService.findUrlByKey(TEST_KEY);

    expect(url).toBeUndefined();
  });


 

  // it('Should send 302 redirect when endpoint parameter is correct', async () => {
  //   const response = await request.get(endpoint+'/'+TEST_KEY);
  //   expect(response.status).toBe(302);
  //   expect(response.headers['location']).toMatch(TEST_URL);;
  //   expect(findUrlByKey)
  // });

  // it('Should send 422 unprocessable when endpoint parameter is wrong', async () => {
  //   const response = await request.get(endpoint+'/test');
  //   expect(response.status).toBe(422);
  //   expect(response.body.message).toMatch('Error: Unable to find URL to redirect to.');
  //   expect(findUrlByKey).toBeCalledTimes(1);
  // });


  //   it('Should send error when url endpoint is more that 200 chars', async () => {
  //     const param = new Array(201).fill('a').join('');
  //     const response = await addHeaders(request.get(endpoint).query({ endpoint: param }));
  //     expect(response.status).toBe(400);
  //     expect(response.body.message).toMatch(/length must/);
  //     expect(response.body.message).toMatch(/200/);
  //     expect(mockBlogFindByUrl).not.toBeCalled();
  //   });

  //   it('Should send error when blog do not exists for url', async () => {
  //     const response = await addHeaders(request.get(endpoint).query({ endpoint: 'xyz' }));
  //     expect(response.status).toBe(400);
  //     expect(response.body.message).toMatch(/do not exists/);
  //     expect(mockBlogFindByUrl).toBeCalledTimes(1);
  //     expect(mockBlogFindByUrl).toBeCalledWith('xyz');
  //   });

  //   it('Should send data when blog exists for url', async () => {
  //     const response = await addHeaders(request.get(endpoint).query({ endpoint: BLOG_URL }));
  //     expect(response.status).toBe(200);
  //     expect(response.body.message).toMatch(/success/);
  //     expect(response.body.data).toBeDefined();
  //     expect(response.body.data).toHaveProperty('_id');
  //     expect(mockBlogFindByUrl).toBeCalledTimes(1);
  //     expect(mockBlogFindByUrl).toBeCalledWith(BLOG_URL);
  //   });
  // });

  // describe('BlogDetail by id route', () => {
  //   beforeEach(() => {
  //     mockFindInfoWithTextById.mockClear();
  //   });

  //   const request = supertest(app);
  //   const endpoint = '/v1/blog/id/';

  //   it('Should send error when invalid id is passed', async () => {
  //     const response = await addHeaders(request.get(endpoint + 'abc'));
  //     expect(response.status).toBe(400);
  //     expect(response.body.message).toMatch(/invalid/);
  //     expect(mockFindInfoWithTextById).not.toBeCalled();
  //   });

  //   it('Should send error when blog do not exists for id', async () => {
  //     const response = await addHeaders(request.get(endpoint + new Types.ObjectId().toHexString()));
  //     expect(response.status).toBe(400);
  //     expect(response.body.message).toMatch(/do not exists/);
  //     expect(mockFindInfoWithTextById).toBeCalledTimes(1);
  //   });

  //   it('Should send data when blog exists for id', async () => {
  //     const response = await addHeaders(request.get(endpoint + BLOG_ID.toHexString()));
  //     expect(response.status).toBe(200);
  //     expect(response.body.message).toMatch(/success/);
  //     expect(response.body.data).toBeDefined();
  //     expect(response.body.data).toHaveProperty('_id');
  //     expect(mockFindInfoWithTextById).toBeCalledTimes(1);
  //   });
});
