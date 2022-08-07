export function throttle(fun: Function, delay: number) {
  let timer = 0;
  return function (this: any, ...args: any[]) {
    if (!timer) {
      timer = window.setTimeout(() => {
        fun.apply(this, args);
        clearTimeout(timer);
        timer = 0;
      }, delay);
    }
  }
}