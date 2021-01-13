import config = require('config');
import { appInit } from './app';

const port = config.get<number>('port');

appInit().then((app) => {
  app.listen(port, async () => {
    console.log(`Short URL listening on port ${port}!`);
  });
});
