/**
 * 节流函数
 *
 * @remarks
 * 节流函数: 高频事件触发，但在n秒内只会执行一次，所以节流会稀释函数的执行频率。
 * 限制函数的执行频次，避免函数触发频率过高导致的响应速度跟不上触发频率，出现延迟，假死或卡顿的现象
 *
 * @param func 执行函数
 * @param delay 延迟时间 ms
 *
 * @return 目标结果
 *
 * @example
 * ```typescript
 * throttle(function(){}, 500, true)
 * ```
 */
export function throttle(func: Function, delay: number): Function {
  let timer: any;
  return function (this: unknown, ...args: any[]) {
    if (!timer) {
      timer = setTimeout(() => {
        func.apply(this, args);
        clearTimeout(timer);
        timer = null;
      }, delay);
    }
  };
}

/**
 * 深拷贝
 *
 * @remarks
 * 深拷贝支出各种数据类型
 *
 * @param source 传入的数据
 *
 * @return 目标数据
 *
 * @example
 * ```typescript
 * let str1 = { arr: [1, 2, 3], obj: { key: 'value' }, fn: function () { return 1; } };
 * let str3 = deepClone(str1);
 * console.log(str3 === str1); // false
 * console.log(str3.obj === str1.obj); // false
 * console.log(str3.fn === str1.fn); // true
 * ```
 */
export function deepClone(source: any) {
  const targetObj: any = source.constructor === Array ? [] : {}; // 判断复制的目标是数组还是对象
  for (const keys in source) {
    // 遍历目标
    if (source.hasOwnProperty(keys)) {
      if (source[keys] && typeof source[keys] === "object") {
        // 如果值是对象，就递归一下
        targetObj[keys] = source[keys].constructor === Array ? [] : {};
        targetObj[keys] = deepClone(source[keys]);
      } else {
        // 如果不是，就直接赋值
        targetObj[keys] = source[keys];
      }
    }
  }
  return targetObj;
}
