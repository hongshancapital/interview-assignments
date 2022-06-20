import * as path from 'path';
import { createServer, Server } from 'http';
import * as express from 'express';
import * as cors from 'cors';
import { useExpressServer } from 'routing-controllers';
import { APPLICATION_NAME, PORT } from './constant';

const requestLogger = (req: express.Request, res: express.Response, next: express.NextFunction) => {
  console.info(`Receiving ${req.method} request ${req.path}`);
  next();
};

export async function initializeHttpServer(): Promise<Server> {
  const app = express();
  app.use(express.json());
  app.use(cors());
  app.use(requestLogger);

  // health check
  app.get('/', (req: express.Request, res: express.Response) => {
    res.status(200).json({ status: APPLICATION_NAME });
  });

  const server = createServer(useExpressServer(app, {
    controllers: [path.resolve(__dirname, '../controller/*.js')],
  }));

  return new Promise((resolve => {
    server.listen(PORT, () => {
      console.log(`Http server is running on http://localhost:${PORT}`);
      resolve(server);
    });
  }));
}
