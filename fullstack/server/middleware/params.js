const logger = require('../utils/logger'); 

// todo something if nessary
const preProcessMap = new Map([
  ['get', (req, next) => {
    logger.debug(`*** ${req.originalUrl}, get, params: ${JSON.stringify(req.query)}`)
    next();
  }],
  ['post', (req, next) => {
    logger.debug(`*** ${req.originalUrl}, post, params: ${JSON.stringify(req.body)}`)
    next();
  }],
  ['option', (req, next) => {
    next();
  }],
  ['put', (req, next) => {
    next();
  }],
  ['delete', (req, next) => {
    next();
  }]
]);

exports.preProcess = (req, res, next) => {
  if (preProcessMap.has(req.method)) {
    preProcessMap.get(req.method)(req, next);
  } else {
    next();
  }
}
