import { Router } from 'express';
import validationMiddleware from '@middlewares/validation.middleware';
import { CreateShortUrlDto } from '@dtos/short-url.dto';
import ShortUrlController from '@controllers/short-url.controller';
import { Routes } from '@interfaces/routes.interface';

class ShortUrlRoute implements Routes {
    public path = '/short';
    public router = Router();
    public shortUrlController = new ShortUrlController();

    constructor() {
        this.initializeRoutes();
    }

    private initializeRoutes() {
        this.router.get(`${this.path}/:short`, this.shortUrlController.getUrl);
        this.router.post(
            `${this.path}`,
            validationMiddleware(CreateShortUrlDto, 'body'),
            this.shortUrlController.create
        );
    }
}

export default ShortUrlRoute;
