import cors from 'cors';
import express from 'express';
import errorMiddleware from '@middlewares/error.middleware';
import { logger } from '@utils/logger';
import { loadDB } from './utils';
import config from '@config';
import { Routes } from '@interfaces/routes.interface';

class App {
    public app: express.Application;
    public env: string;
    public port: string | number;

    constructor(routes: Routes[]) {
        this.app = express();
        this.env = config.APP_ENV || 'development';
        this.port = config.PORT || 3000;

        this.env !== 'test' && this.connectToDatabase();
        this.initializeMiddlewares();
        this.initializeRoutes(routes);
        this.initializeErrorHandling();
    }

    public listen() {
        this.app.listen(this.port, () => {
            logger.info('=================================');
            logger.info(`======= ENV: ${this.env} =======`);
            logger.info(`🚀 App listening on the port ${this.port}`);
            logger.info('=================================');
        });
    }

    public getServer() {
        return this.app;
    }

    private async connectToDatabase() {
        await loadDB();
    }

    private initializeMiddlewares() {
        this.app.use(cors({ origin: config.ORIGIN }));
        this.app.use(express.json());
        this.app.use(express.urlencoded({ extended: true }));
    }

    private initializeRoutes(routes: Routes[]) {
        routes.forEach((route) => {
            this.app.use('/', route.router);
        });
    }

    private initializeErrorHandling() {
        this.app.use(errorMiddleware);
    }
}

export default App;
