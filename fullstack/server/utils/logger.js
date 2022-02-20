const chalk = require('chalk');
const args = process.argv.slice(2);
const info = new Map([
  ['error', err => console.log(chalk.red(err))],
  ['log', msg => console.log(chalk.green(msg))],
  ['debug', msg => {
    if (args.includes('--debug')) {
      console.log(chalk.orange(msg))
    }
  }]
]);

// todo: report to log server if necessary
// todo: pipe to localfile asynchronously

module.exports = {
  error: info.get('error'),
  log: info.get('log'),
  debug: info.get('debug')
};
