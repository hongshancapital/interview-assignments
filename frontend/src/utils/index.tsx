/**
 * 递增计数器，溢出归0
 * @param max 最大值
 * @param current 当前值
 */
export const roller = (max: number, current: number) => {
  return (current + 1) % max;
};
