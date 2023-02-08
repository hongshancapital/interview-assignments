/* eslint-disable @typescript-eslint/no-var-requires */
const development = require('./webpack.dev');
const production = require('./webpack.prod');

module.exports = {
    development,
    production
};
