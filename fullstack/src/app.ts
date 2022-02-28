import express from 'express';
import { router } from './controllers/short-url';
const app = express();
// handle application/json
app.use(express.json());

// hook up routes
app.use(router);

export { app };
