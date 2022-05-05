import {Router} from 'express';
import TinyurlController from '../controllers/TinyControll';

class TinyurlRoute {
    private router: Router;

    constructor() {
        this.router = Router();
        this.setupRouter();
    }

    private setupRouter() {
        this.router.get('/:shortId', async (req, res) => {
            await TinyurlController.GetTinyurl(req, res);
        });

        this.router.post('/', async (req, res) => {
            await TinyurlController.TinyUrl(req, res);
        });
    }

    getRouter() {
        return this.router;
    }
}

export default new TinyurlRoute();
