import axios, { AxiosInstance } from 'axios';
import { PrismaClient } from '@prisma/client';
import { startApp, stopApp } from '@/app';
import { encode } from '@/utils/short-id-converter';

describe('/api', () => {
  let axiosAPIClient: AxiosInstance;
  let prisma: PrismaClient;

  beforeAll(async () => {
    const addressInfo = await startApp();
    const axiosConfig = {
      baseURL: `http://127.0.0.1:${addressInfo.port}`,
      validateStatus: () => true, //Don't throw HTTP exceptions. Delegate to the tests to decide which error is acceptable
    };
    axiosAPIClient = axios.create(axiosConfig);
    prisma = new PrismaClient();
  });

  afterAll(async () => {
    await stopApp();
    await prisma.$disconnect();
  });

  afterEach(async () => {
    await prisma.url.deleteMany();
  });

  describe('GET /short-url/:short-id', () => {
    test('When asked for an existing shortId and accept json, Then should retrieve it and receive 200 response', async () => {
      const urlToAdd = 'https://example.com/';
      const {
        data: { shortId },
      } = await axiosAPIClient.post(`/api/short-urls`, { url: urlToAdd });

      const getResponse = await axiosAPIClient.get(`/api/short-urls/${shortId}`, {
        headers: { Accept: 'application/json' },
      });

      expect(getResponse).toMatchObject({
        status: 200,
        data: {
          url: 'https://example.com/',
        },
      });
    });

    test('When asked for an existing shortId and accept htm;, Then should receive a 302 redirect to that url', async () => {
      const urlToAdd = 'https://example.com/';
      const {
        data: { shortId },
      } = await axiosAPIClient.post(`/api/short-urls`, { url: urlToAdd });

      const response = await axiosAPIClient.get(`/api/short-urls/${shortId}`, {
        maxRedirects: 0,
        headers: { Accept: 'text/html' },
      });

      expect(response).toMatchObject({
        status: 302,
        headers: {
          location: urlToAdd,
        },
      });
    });

    test('When asked for an non-existing url, Then should receive 404 response', async () => {
      const nonExistingUrlShortId = encode(9999999);

      const httpResponse = await axiosAPIClient.get(`/api/short-urls/${nonExistingUrlShortId}`, {
        maxRedirects: 0,
        headers: { Accept: 'text/html' },
      });
      expect(httpResponse.status).toBe(404);

      const jsonResponse = await axiosAPIClient.get(`/api/short-urls/${nonExistingUrlShortId}`, {
        maxRedirects: 0,
        headers: { Accept: 'application/json' },
      });
      expect(jsonResponse.status).toBe(404);
    });

    test('When asked for an invalid url shortId, Then should receive 400 response', async () => {
      const invalidUrlShortId = 12345678;

      const httpResponse = await axiosAPIClient.get(`/api/short-urls/${invalidUrlShortId}`, {
        maxRedirects: 0,
        headers: { Accept: 'text/html' },
      });
      expect(httpResponse.status).toBe(400);

      const jsonResponse = await axiosAPIClient.get(`/api/short-urls/${invalidUrlShortId}`, {
        maxRedirects: 0,
        headers: { Accept: 'application/json' },
      });
      expect(jsonResponse.status).toBe(400);
    });
  });

  describe('POST /api/short-urls', () => {
    test('When adding a new valid url, Then should get back approval with 200 response', async () => {
      const urlToAdd = 'https://example.com/';

      const response = await axiosAPIClient.post('/api/short-urls', { url: urlToAdd });

      expect(response).toMatchObject({
        status: 200,
        data: {
          id: expect.any(Number),
          url: urlToAdd,
          shortId: expect.any(String),
        },
      });
      expect(response.data.shortId).toHaveLength(8);
    });

    test('When adding an invalid url, stop and return 400', async () => {
      const invalidUrl = 'example.com';

      const response = await axiosAPIClient.post('/api/short-urls', { url: invalidUrl });

      expect(response).toMatchObject({
        status: 400,
        data: {
          message: 'Invalid URL',
        },
      });
    });
  });
});
