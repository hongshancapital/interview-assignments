const path = require('path');
const dotenv = require('dotenv');

module.exports = async () => {
  const env = dotenv.config({ path: path.resolve(__dirname, '.env.test') });
  if (env.error) {
    throw env.error;
  }
};
