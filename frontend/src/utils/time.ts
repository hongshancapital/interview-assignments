export const sleep: (delay: number) => Promise<boolean> = (delay: number) => {
  return new Promise(resolve => {
    setTimeout(() => {
      resolve(true)
    }, delay);
  });
};
