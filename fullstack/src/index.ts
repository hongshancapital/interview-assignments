import * as express from 'express';
import middlewares from './middlewares';
import mocks from './mocks';
import sUrl from './sUrl';

require('dotenv').config();

const app = express();

if (process.env.MOCK === '1') {
  mocks(app);
}

middlewares(app);
sUrl(app);

app.listen(3580, () => {
  console.log('listening on port 3580!');
});

export default app;
