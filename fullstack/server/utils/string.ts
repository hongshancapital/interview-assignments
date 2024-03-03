const twoByteReg = /^[\u0000-\u00ff]$/;

export const getBytes = (str: string) => {
  let count = 0;
  let i = 0;
  const length = str.length;
  while (i < length) {
    if (twoByteReg.test(str[i])) {
      count += 1;
    } else {
      count += 2;
    }

    i++;
  }
  return count;
}
