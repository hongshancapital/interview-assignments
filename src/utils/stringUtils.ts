function getStr62ByNumber(number) {
  const chars = 'abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789'.split('');
  const radix = chars.length;
  let qutient = +number;
  let arr:string[] = [];
  do {
    const mod = qutient % radix;
    qutient = (qutient - mod) / radix;
    arr.unshift(chars[mod]);
  } while (qutient);
  return arr.join('');
}

export default { getStr62ByNumber };
