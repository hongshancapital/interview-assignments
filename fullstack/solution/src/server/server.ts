import * as http from 'http';
import 'reflect-metadata';
import container from './inversify.config';
import App from './app';
import { logger } from '../logger';
import { SERVICE_PORT } from '../config';

const { app } = container.get<App>(App);

http.createServer(app).listen(SERVICE_PORT, () => {
  logger.info(`Express server listening on port ${SERVICE_PORT}`);
});
