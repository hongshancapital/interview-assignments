import { Router } from 'express';

import ShortUrlController from '../controllers/ShortUrlController';

class ShortUrlRoute {
    private router: Router;

    constructor() {
        this.router = Router();
        this.setupRouter();
    }

    private setupRouter() {
        this.router.get('/:shortId', ShortUrlController.getUrlbyShortId);

        this.router.post('/', ShortUrlController.shortenUrl);
    }

    getRouter() {
        return this.router;
    }
}

export default new ShortUrlRoute();
