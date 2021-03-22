import { i64BitTable, urlRegx } from "../const";
/**
 * convert a integer to base64 string
 * 
 * @param value integer key
 * @returns base64 short key
 */
function convertToShortId(value: number): string {
  if (value == 0) return i64BitTable[0] + "";
  let retValue = "";
  while (value != 0) {
    let iValue = value % 64;
    let temp;

    temp = i64BitTable[iValue];
    retValue = temp + retValue;
    value = Math.floor(value / 64);
  }
  return retValue;
}

function validateUrl(url: string): boolean {
  return urlRegx.test(url);
}

export { convertToShortId, validateUrl };
