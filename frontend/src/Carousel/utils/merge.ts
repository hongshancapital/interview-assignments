export function merge<S = any>(source: S | undefined, target: S): S {
  if (Array.isArray(source)) {
    if (Array.isArray(target)) {
      const sl = source.length,
        tl = target.length;
      const arr = [] as any[];
      for (let i = 0; i < sl || i < tl; i++) {
        arr.push(merge(source[i], target[i]));
      }
      return arr as any;
    }
    return source;
  } else if (typeof source === "object") {
    if (source === null) {
      return target;
    }
    if (Object.prototype.toString.call(target) === "[object Object]") {
      const obj = Object.assign({}, source);
      for (let key in target) {
        if (obj.hasOwnProperty(key)) {
          obj[key] = merge(source[key], target[key]);
        } else {
          obj[key] = target[key];
        }
      }
      return obj;
    }
    return source;
  }
  return target;
}
