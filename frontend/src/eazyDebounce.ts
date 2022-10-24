const debounce = <T extends (...args: any) => any>(fn: T) => {
  let timer = 0;
  return (...args: Parameters<T>) => {
    if (timer) clearTimeout(timer);
    timer = (setTimeout as Window['setTimeout'])(() => {
      fn(...args);
    }, 50);
  }
};

export default debounce;