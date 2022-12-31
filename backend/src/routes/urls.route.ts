import { Router } from 'express';
import UrlController from '@/controllers/urls.controller';
import { Routes } from '@interfaces/routes.interface';

class UrlRoute implements Routes {
  public path = '/url';
  public router = Router();
  public urlController = new UrlController();

  constructor() {
    this.initializeRoutes();
  }

  private initializeRoutes() {
    this.router.get(`${this.path}`, this.urlController.getUrls);
    this.router.get(`${this.path}/:url`, this.urlController.getByShortUrl);
    this.router.post(`${this.path}`, this.urlController.createWithLongUrl);
  }
}

export default UrlRoute;
