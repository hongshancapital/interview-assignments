function randomWord(min, ruledOutStr) {
  let [str, pos, arr] = [
    '',
    0,
    '0123456789bcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ',
  ];
  if (ruledOutStr) {
    ruledOutStr.map((item) => {
      arr = arr.split(item).join('');
    });
  }

  // // 随机产生
  // if (randomFlag) {
  //   range = Math.round(Math.random() * (max - min)) + min;
  // }
  for (let i = 0; i < min; i++) {
    pos = Math.round(Math.random() * (arr.length - 1));
    str += arr[pos];
  }
  return str;
}

export function EncodeStr(number) {
  if (typeof number !== 'number') return;
  const [randomInsertStr, chars, arr] = [
    'a',
    '0123456789bcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ',
    [],
  ];
  const radix = chars.length;
  let qutient = +number;

  do {
    const mod = qutient % radix;
    qutient = (qutient - mod) / radix;
    arr.unshift(chars.charAt(mod));
  } while (qutient);
  let codeStr = arr.join('');
  codeStr =
    codeStr.length < 6
      ? `${codeStr}${randomInsertStr}${randomWord(5 - codeStr.length, [
          randomInsertStr,
        ])}`
      : codeStr;
  return codeStr;
}
