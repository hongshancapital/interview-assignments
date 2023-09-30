import request from 'supertest';
import mongoose from 'mongoose';
import { ShortUrlController } from '../src/controllers/shortUrl';
import { app } from '../src/app';

const shortUrlController = new ShortUrlController();

describe('API Integration test', () => {
  beforeAll((done) => {
    mongoose.connect('mongodb://localhost:27017/shortUrl', { }, (err) => {
      if (err) {
        console.error(err);
        process.exit(1);
      }

      done();
    });
  });

  afterAll((done) => {
    mongoose.connection.dropDatabase(() => {
      mongoose.connection.close(() => done());
    });
  });

  describe('POST /shortUrls', () => {
    test('should return 400 status code when longUrl parameter is missing', () => {
      return request(app).post('/shortUrls').expect(400);
    });

    test('should return 200 status code with a short url when longUrl parameter is provided', async () => {
      const longUrl = 'https://www.example.com';
      const response = await request(app).post('/shortUrls').send({ longUrl });

      expect(response.status).toBe(200);
      expect(response.body).toHaveProperty('shortUrl');
    });
  });

  describe('GET /shortUrls/:shortUrlId', () => {
    test('should return 400 status code when shortUrlId parameter is missing', async () => {
      request(app).get('/shortUrls').expect(400);
    });

    test('should return 404 status code when the short url is not found in database', async () => {
      const response = await request(app).get('/shortUrls/abcdefgh');

      expect(response.status).toBe(404);
    });

    test('should redirect to long url when the short url is found in database', async () => {
      const longUrl = 'https://www.example.com';
      const shortUrl = await shortUrlController.createShortUrl(longUrl);

      const response: any = await request(app).get(`/shortUrls/${shortUrl}`).redirects(1);

      expect(response.status).toBe(200);
      expect(response.body).toHaveProperty('longUrl');
    });
  });
});