import express from 'express';
import qs from 'qs';
import bodyParser from 'body-parser';

const server = express();
server.settings('query parser', (queryString: string) => {
  return qs.parse(queryString);
});
server.use(bodyParser.json);

export default server;