import { app } from './app';
import { ready as mysqlReady } from './libs/db-connection';
// start up
app.listen(8080, '127.0.0.1', async () => {
  // make sure deps are started correctly
  await mysqlReady();
  console.log('Server started listening on 8080');
});
