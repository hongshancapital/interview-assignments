/**
 * 深度笔记判断两个对象是否相等
 * @param a
 * @param b
 * @returns
 */
export function isEqual(a: any, b: any): boolean {
  // 判断类型是否相等
  if (typeof a !== typeof b) {
    return false;
  }

  // 如果是基本类型，直接比较值
  if (typeof a !== "object" || a === null || b === null) {
    return a === b;
  }

  // 如果是数组类型，逐个比较元素
  if (Array.isArray(a)) {
    if (a.length !== b.length) {
      return false;
    }
    for (let i = 0; i < a.length; i++) {
      if (!isEqual(a[i], b[i])) {
        return false;
      }
    }
    return true;
  }

  // 如果是对象类型，逐个比较属性
  const keys1 = Object.keys(a);
  const keys2 = Object.keys(b);
  if (keys1.length !== keys2.length) {
    return false;
  }
  for (let key of keys1) {
    if (!b.hasOwnProperty(key) || !isEqual(a[key], b[key])) {
      return false;
    }
  }

  return true;
}
