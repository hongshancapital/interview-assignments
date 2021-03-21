import { i64BitTable, urlRegx } from "../const";

function convertToShortId(value: number): string {
  if (value == 0) return i64BitTable[0] + "";
  let retValue = "";
  while (value != 0) {
    
    let iValue = value%64;
    let temp;

    temp = i64BitTable[iValue];
    retValue = temp + retValue;
    value = Math.floor(value/64);
  }
  return retValue;
}

// >> support 32bit
function convertToShortId2(value: number): string {
  if (value == 0) return i64BitTable[0] + "";
  let retValue = "";
  while (value != 0) {
    let iValue = value & 0x000000000000003f;
    let temp;

    temp = i64BitTable[iValue];
    retValue = temp + retValue;
    value = value >> 32;
    value = Math.abs(value);
  }
  return retValue;
}
function validateUrl(url: string): boolean {
  return urlRegx.test(url);
}
export  {
  convertToShortId,
  validateUrl,
};
