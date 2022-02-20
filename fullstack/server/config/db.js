let config = null;

// should have different config
if (process.env.NODE_ENV === 'development') {
  config = require('./db.config');
} else {
  config = require('./db.config');
}

module.exports = config;
