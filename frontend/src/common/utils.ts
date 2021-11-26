export const convertDuration = (originUnit: Time.Unit, targetUnit: Time.Unit, num: number): number => {
  return num * Time.Duration[originUnit] / Time.Duration[targetUnit];
}