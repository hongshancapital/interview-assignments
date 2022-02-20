const open = require('open');
const cors = require("cors");
const express = require('express');
const bodyParser = require('body-parser');
const logger = require('./server/utils/logger');
const db = require('./server/models');
const params = require('./server/middleware/params');

const port = 3001;
const host = '127.0.0.1';
const app = express();
const corsOptions = {
  origin: "http://127.0.0.1:3000"
};

const shortLinkRouter = require('./server/router/short-link');

db.sequelize.sync();

app.use(cors(corsOptions));
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(params.preProcess);

app.use('/api', shortLinkRouter);

app.listen(port, err => {
  if (err) {
    logger.error('Server error: ', err);
    return;
  }
  logger.log(`🚀🚀🚀 Server is running on port ${port}`);

  open(`http://${host}:${port}/api`);
});
