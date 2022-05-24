import 'reflect-metadata';
import { initializeDb } from './component/db';
import { initializeHttpServer } from './component/http';

initializeDb()
  .then(() => initializeHttpServer())
  .catch(error => console.log(`Failed to start application: ${JSON.stringify(error)}`));
