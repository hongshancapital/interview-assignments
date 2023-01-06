const path = require('path');
const { execSync } = require('child_process');
const dockerCompose = require('docker-compose');
const isReachable = require('is-reachable');

module.exports = async () => {
  console.time('global-setup');
  console.log();

  const dbIsReachable = await isReachable('localhost:5433');
  if (!dbIsReachable) {
    console.log('Up docker-compose...');
    await dockerCompose.upAll({
      cwd: path.join(__dirname),
      log: true,
    });

    await dockerCompose.exec('db', ['sh', '-c', 'until pg_isready ; do sleep 1; done'], {
      cwd: path.join(__dirname),
    });

    execSync('npm run prisma:migrate:test');
  }

  console.timeEnd('global-setup');
};
