/**
 * Module dependencies.
 */
import express from "express";
import compression from "compression";
import bodyParser from "body-parser";
import logger from "morgan";
import chalk from "chalk";
import errorHandler from "errorhandler";
import dotenv from "dotenv";

/**
 * Load environment variables from .env file, where API keys and passwords are configured.
 */
dotenv.load({ path: '.env' });

/**
 * Controllers (route handlers).
 */
const userController = require('./controllers/mysql');

/**
 * Create Express server.
 */
const app = express();

/**
 * Express configuration.
 */
app.set('port', process.env.PORT || 3030);
app.use(compression());
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

/**
 * Primary app routes.
 */
app.get('/login', userController.getLogin);
app.post('/login', userController.postLogin);

/**
 * Error Handler.
 */
if (process.env.NODE_ENV === 'development') {
  // only use in development
  app.use(errorHandler());
} else {
  app.use((req, res, next) => {
    res.status(500).send('Server Error');
  });
}

/**
 * Start Express server.
 */
app.listen(app.get('port'), () => {
  console.log('%s App is running at http://localhost:%d in %s mode', chalk.green('✓'), app.get('port'), app.get('env'));
  console.log('  Press CTRL-C to stop\n');
});

export { app };
