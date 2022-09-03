export const classNames = (...args: string[]) => {
  if (!args || !args.length) {
    return '';
  }
  return args.join(' ');
};
