import chalk from 'chalk';

const emptyFunc = () => {};
const args = process.argv.slice(2);
const info = new Map([
  ['error', (err: string): void => console.log(chalk.red(err))],
  ['log', (msg: string): void => console.log(chalk.green(msg))],
  ['debug', (msg: string, show?: boolean): void => {
    if (args.includes('--debug') || show) {
      console.log(chalk.yellow(msg));
    }
  }]
]);

// todo: report to log server if necessary
// todo: pipe to localfile asynchronously,and divide logs by dateTime

export default {
  error: info.get('error') || emptyFunc,
  log: info.get('log') || emptyFunc,
  debug: info.get('debug') || emptyFunc
};
