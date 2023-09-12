const debounce = (func: Function, time: number = 500, immediate = false) => {
  let timer: number | null = null;
  return (...args: any) => {
    if (timer) window.clearTimeout(timer);
    if (immediate) {
      if (!timer) func.apply(this, args);
      timer = window.setTimeout(() => {
        timer = null;
      }, time);
    } else {
      timer = window.setTimeout(() => {
        func.apply(this, args);
      }, time);
    }
  };
};

export default debounce;
