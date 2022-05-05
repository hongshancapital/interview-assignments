import express from 'express';
import helmet from 'helmet';
import bodyParser from 'body-parser';
import cors from 'cors';

import Singleton from './utils/Singleton';
import router from './routers/TinyRouter';

class ApiApp extends Singleton {
    private application: express.Application;

    constructor() {
        super();
        this.application = express();
        this.setupGlobalMiddleware();
        this.setupRouters();
    }

    start(port: string | number = 3000) {
        return this.application.listen(port, () => {
            console.log(`listening on port ${port}`);
        });
    }

    getApplication() {
        return this.application;
    }

    private setupGlobalMiddleware() {
        this.application.use(cors());
        this.application.use(helmet());
        this.application.use(bodyParser.json());
        this.application.use(bodyParser.urlencoded({extended: true}));
    }

    private setupRouters() {
        this.application.get('/', (_, res) => {
            res.json({message: 'Welcome!'});
        });

        this.application.use('/api', router.getRouter());
    }
}

const ApiAppinstance = ApiApp.getInstance();
export default ApiAppinstance;
