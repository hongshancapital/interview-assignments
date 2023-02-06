export const createIncArray = (
  length: number,
  start: number = 0
): Array<number> => {
  return Array.from(Array(length), (v, k) => k + start);
};

export const classnames = (
  ...args: (string | Record<string, boolean>)[]
): string => {
  const classes: string[] = [];

  for (let i = 0; i < args.length; i++) {
    const arg = args[i];

    if (typeof arg === 'string') {
      classes.push(arg);
    } else if (typeof arg === 'object') {
      for (const key of Object.keys(arg)) {
        if (arg[key]) {
          classes.push(key);
        }
      }
    }
  }

  return classes.join(' ');
};
