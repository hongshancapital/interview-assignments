import * as http from 'http';
import 'reflect-metadata';
import container from './inversify.4test.config';
import App from '../server/app';

import { logger } from '../logger';
import { SERVICE_PORT } from '../config';

const { app } = container.get<App>(App);

http.createServer(app).listen(SERVICE_PORT, () => {
  logger.info(`Express server listening on port ${SERVICE_PORT}`);
});
