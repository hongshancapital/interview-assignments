const twoByteReg = /^[\u0000-\u00ff]$/;

const getBytes = str => {
  let count = 0;
  let i = 0;
  const length = str.length;
  while (i < length) {
    if (twoByteReg.test(str[i])) {
      count += 2;
    } else {
      count += 1;
    }

    i++;
  }
  return count;
}

module.exports = {
  getBytes
};
