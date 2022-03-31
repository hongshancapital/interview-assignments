export const isDev = (() => process.env.NODE_ENV !== 'production')();

export const isFunc = (val: unknown): val is CallableFunction =>
  typeof val === 'function';

export const warning = (msg: string) => {
  if (!msg) return;

  console.warn(`Warning: ${msg}`);
};
