const isCI = require('is-ci');
const dockerCompose = require('docker-compose');
const path = require('path');
const dotenv = require('dotenv');

module.exports = async () => {
  console.time('global-teardown');

  if (!process.env.SKIP_SETUP_DOCKER_COMPOSE) {
    if (isCI) {
      console.log('Shutdown Docker Compose...');
      dotenv.config({ path: path.resolve(__dirname, '.env.test') });
      await dockerCompose.down({
        cwd: path.join(__dirname),
        log: true,
      });
    }
  }

  console.timeEnd('global-teardown');
};
