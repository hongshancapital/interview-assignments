/**
 * react钩子单独使用时，不方便对比新旧值
 * watcher方法会返回一个updateValue函数，当value变化时，触发回调
 * @param initVal 初始值
 * @param callback 数值变化后的毁掉
 * @param isEqual 相等判定
 * @returns updateValue: (val: T) => void
 */
const watcher = <T>(
  initVal: T,
  callback: (newVal: T, oldVal: T) => void,
  isEqual: (newVal: T, oldVal: T) => boolean = (newVal, oldVal) => newVal === oldVal) => {
  let value = initVal;
  return (val: T) => {
    if(isEqual(val, value)) return;
    const oldVal = value;
    value = val;
    callback(value, oldVal);
  };
};
// 判断值是否有意义
const isDef = (val: unknown) => !!(val || val === 0);

// 获取当前时间戳
const getTimestamp = () => +new Date();

// 解析className，解析方式参考vue的class解析规则
type ClassObj = Record<string, boolean>
const parseClassName = (classDesc: (string|ClassObj|undefined)[]|ClassObj) => {
  const parseClassObj = (obj: ClassObj) => {
    const keys = Object.keys(obj);
    return keys.reduce((str, key) => {
      if(obj[key]) {
        return `${str} ${obj[key]}`;
      }
      return str;
    }, '');
  };
  const parseClassDesc = (classInfo?: string|(string|ClassObj|undefined)[]|ClassObj): string => {
    if(!classInfo) return '';
    if(typeof classInfo === 'string') return (classInfo as string);
    if(Array.isArray(classInfo)) return classInfo.map(parseClassDesc).filter(Boolean).join(' ');
    return parseClassObj(classInfo);
  };
  return parseClassDesc(classDesc);
};
export {
  watcher,
  isDef,
  getTimestamp,
  parseClassName
};