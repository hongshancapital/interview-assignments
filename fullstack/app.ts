/**
 * Module dependencies.
 */
import express from "express";
import mongoose from "mongoose";
import bodyParser from "body-parser";
import logger from "morgan";
import chalk from "chalk";
import dotenv from "dotenv";
import path from "path";

/**
 * Load environment variables from .env file, where API keys and passwords are configured.
 */
dotenv.config({
  path: path.resolve(process.cwd(), ".env"),
});

/**
 * Controllers (route handlers).
 */
const shortLinkController = require('./controllers/shortlink');

/**
 * Create Express server.
 */
const app = express();

/**
 * Connect to MongoDB.
 */
mongoose.connect(process.env.MONGODB_URI || '');
mongoose.connection.on('error', (err) => {
  console.error(err);
  console.log('%s MongoDB connection error. Please make sure MongoDB is running.');
  process.exit();
});

/**
 * Express configuration.
 */
app.set('host', process.env.OPENSHIFT_NODEJS_IP || '0.0.0.0');
app.set('port', process.env.PORT || process.env.OPENSHIFT_NODEJS_PORT || 8080);
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

/**
 * Primary app routes.
 */
app.get('/:shortLink', shortLinkController.getShortLink);
app.post('/shortlink', shortLinkController.postShortLink);


/**
 * Start Express server.
 */
app.listen(app.get('port'), () => {
  console.log('%s App is running at http://localhost:%d in %s mode', chalk.green('✓'), app.get('port'), app.get('env'));
  console.log('  Press CTRL-C to stop\n');
});

export { app };
