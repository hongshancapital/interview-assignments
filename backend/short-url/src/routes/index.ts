import { Express } from 'express';
import { body, query } from 'express-validator';
import { createShortUrl, getLongUrl } from '../controller/shortUrl.controller';
class Router {
  constructor() {
  }
  public async routes(app: Express) {
    app.post(
      '/shortUrl',
      // longUrl must be an Url and short than 4098
      body('longUrl').notEmpty().isURL().isLength({ max: 4096 }),
      
      createShortUrl
    );

    app.get(
      '/longUrl',
      // shortUrl must not empty and short than 4098
      query('shortUrl').notEmpty().isLength({ max: 8 }),

      getLongUrl
      );
  }
}

const router = new Router();

export { router };