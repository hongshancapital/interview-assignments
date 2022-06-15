
export class Helper {
  /**
   * 获取数组的第一个元素。
   *
   * @author microld
   *
   * @version 1.0.0
   *
   * @param array - 要查询的数组
   *
   * @returns 返回数组的第一个元素。
   *
   * @example
   *
   * Helper.head([1, 2, 3]);
   * // => 1
   *
   * Helper.head([]);
   * // => undefined
   *
   * @public
   */
  public static head<T>(array: T[]): T | undefined {
    return (array && array.length) ? array[0] : undefined;
  }

  /**
   * 获取数组的第一个元素。
   *
   * @author microld
   *
   * @version 1.0.0
   *
   * @param array - 要查询的数组
   *
   * @returns 返回数组的最后一个元素。
   *
   * @example
   *
   * Helper.last([1, 2, 3]);
   * // => 3
   *
   * Helper.last([]);
   * // => undefined
   *
   * @public
   */
  public static last<T>(array: T[]): T | undefined {
    return Array.isArray(array) && array.length ? array[array.length - 1] : undefined;
  }

  /**
   * 获取数组索引 `n` 处的元素。 如果 `n` 为负数，则返回倒数第 `n` 个元素。
   *
   * @author microld
   *
   * @version 1.0.0
   *
   * @param array - 要查询的数组
   *
   * @returns 返回数组的第 n 个元素。
   *
   * @example
   *
   * Helper.nth([1, 2, 3, 4], 1);
   * // => 2
   *
   * Helper.nth([1, 2, 3, 4], -2);
   * // => 3
   *
   * @public
   */
  public static nth<T>(array: T[], n: number): T | undefined {
    if (array && array.length) {
      n += n < 0 ? array.length : 0;
      return array[n];
    }

    return undefined;
  }

  /**
   * 获取数组索引 `n` 处的元素。 如果 `n` 为负数，则返回倒数第 `n` 个元素。
   *
   * @author microld
   *
   * @version 1.0.0
   *
   * @param array - 要查询的数组
   *
   * @returns 返回数组的第 n 个元素。
   *
   * @example
   *
   * Helper.nth([1, 2, 3, 4], 1);
   * // => 2
   *
   * Helper.nth([1, 2, 3, 4], -2);
   * // => 3
   *
   * @public
   */
  public static at<T>(array: T[], n: number): T | undefined {
    n = Math.trunc(n) || 0;
    if (n < 0) n += array.length;
    if (n < 0 || n >= array.length) return undefined;
    return array[n];
  }
}