import express from 'express';
import { router } from './routes/index';
import { config } from '../src/common/config/index'; 

const app = express();

app.use(express.json());

const PORT = config.port;

app.listen(PORT, async () => {
  console.log(`App is running at http://localhost:${PORT}`);
  router.routes(app);
});

export { app }