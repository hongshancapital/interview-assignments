const isCI = require('is-ci');
const dockerCompose = require('docker-compose');
const path = require('path');

module.exports = async () => {
  console.time('global-teardown');
  if (isCI) {
    console.log('Shutdown Docker Compose...');
    await dockerCompose.down({
      cwd: path.join(__dirname),
      log: true,
    });
  }
  console.timeEnd('global-teardown');
};
