import dotenv from 'dotenv';
import {createServer} from './utils/server';

dotenv.config();

const port = process.env.PORT || 3000;

createServer()
  .then(server => {
    server.listen(port, () => {
      console.log(`⚡️[server]: Server is running at https://localhost:${port}`);
    });
  }).catch(err => {
  console.error(`Error: ${err}`)
})

