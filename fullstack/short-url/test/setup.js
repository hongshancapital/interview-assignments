const path = require('path');
const { execSync } = require('child_process');
const dockerCompose = require('docker-compose');
const isReachable = require('is-reachable');
const dotenv = require('dotenv');

module.exports = async () => {
  console.time('global-setup');
  console.log();

  if (!process.env.SKIP_SETUP_DOCKER_COMPOSE) {
    const dbIsReachable = await isReachable('localhost:5433');
    if (!dbIsReachable) {
      console.log('Up docker-compose...');

      dotenv.config({ path: path.resolve(__dirname, '.env.test') });
      await dockerCompose.upAll({
        cwd: path.join(__dirname),
        log: true,
      });

      await dockerCompose.exec('db', ['sh', '-c', 'until pg_isready ; do sleep 1; done'], {
        cwd: path.join(__dirname),
      });

      execSync('npm run prisma:migrate:test');
    }
  } else {
    console.warn('skip up docker-compose!');
  }

  console.timeEnd('global-setup');
};
