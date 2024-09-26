/**
 * 向上取整
 * @param {number} quotientNum 除法的商
 * @return {number}
 */
export function ceil(quotientNum: number): number {
  const floorNum = Math.floor(quotientNum);
  const result = quotientNum > floorNum ? floorNum + 1 : floorNum;
  return result;
}

/**
 * 求一个数组是否是另一个数组的子集
 * @param target 目标数组
 * @param search 要查询的数组
 * @returns
 */
export function isIncluded(
  target: Array<string>,
  search: Array<string>
): boolean {
  const result: string = target.find((item) => {
    return search.includes(item);
  });
  return result != null;
}
