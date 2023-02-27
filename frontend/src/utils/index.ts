/**
 * @param func 功能函数
 * @param delay 延时时间
 * @param thisArg 功能函数内的this对象
 * @returns 
 */
export function debounce (func:Function, delay:number,  thisArg?: any):Function {
  let timeout: any;
  return function(...rest: any):void {
    timeout && clearTimeout(timeout);
    timeout = setTimeout(() => {
      func.apply(thisArg, ...rest)
    }, delay)
  }
}

/**
 * @param func 功能函数
 * @param delay 延时时间
 * @param thisArg 功能函数内的this对象
 * @returns 
 */
export function throttle(func:Function, delay:number,  thisArg?: any):any {
  let canImplement: boolean = true;
  return function(...rest: any) {
    if(canImplement) {
      canImplement = false;
      setTimeout(() => {
        func.apply(thisArg, ...rest);
        canImplement = true;
      }, delay)
    }
  }
}


