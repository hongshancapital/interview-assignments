import { port } from './config';
import app from './app';

app
  .listen(port, () => {
    console.log('running on '+ port);
  })
  .on('error', (e) => console.log(e));
