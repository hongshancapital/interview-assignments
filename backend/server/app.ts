import { Application } from 'egg';
import errorCode from './app/enum/errorCode';


export default class AppBootHook {
    public app: Application;
    constructor(app) {
        this.app = app;
    }
    willReady() {
        const app = this.app;
        app.errorCode = errorCode;
    }
}
