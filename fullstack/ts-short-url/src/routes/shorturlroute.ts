import { Router } from "express";
import  ShortUrlCtrl  from "../controllers/shorturlctrl"

export class ShortUrlRoute {
    router = Router();
    shortUrlCtrl = new ShortUrlCtrl();

    constructor() {
        this.intializeRoutes();
    }

    intializeRoutes() {
        this.router.route('/ShortUrl').post(this.shortUrlCtrl.ShortUrlPro);
        this.router.route('/originalUrl').post(this.shortUrlCtrl.OriginalUrlPro);
    }
}

export default new ShortUrlRoute().router;