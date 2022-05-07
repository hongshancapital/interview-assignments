function debounce(fn: Function, delay: number): any {
    let timer: any = null;
    const debounced = function (this: any, ...args: any) {
      const context = this;
      timer && clearTimeout(timer);
      timer = setTimeout(() => {
        fn.apply(context, args);
      }, delay);
    };
  
    debounced.cancle = () => {
      if (timer) {
        clearTimeout(timer);
        timer = null;
      }
    };
  
    return debounced;
  }
  
  export default debounce;
  