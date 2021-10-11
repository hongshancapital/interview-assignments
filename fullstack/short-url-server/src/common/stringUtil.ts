function string10to62(number) {
  const chars = 'B81E3K2506cRbahefdiVklmzprsxtuvwy4oDCNBAF7gGHIXLM9nOPZQqSTUWYG'.split('');
  const radix = chars.length;
  let qutient = 3843 + number;
  const arr = [];
  do {
    const mod = qutient % radix;
    qutient = (qutient - mod) / radix;
    arr.unshift(chars[mod]);
  } while (qutient);
  if (arr.length <= 8) {
    let codeStr = arr.join('');
    return codeStr;
  } else {
    return '00';
  }
}

function validURL(str) {
  const pattern = new RegExp(
    '^(https?:\\/\\/)?' + // protocol
    '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|' + // domain name
    '((\\d{1,3}\\.){3}\\d{1,3}))' + // OR ip (v4) address
    '(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*' + // port and path
    '(\\?[;&a-z\\d%_.~+=-]*)?' + // query string
      '(\\#[-a-z\\d_]*)?$',
    'i',
  ); // fragment locator

  return str && !!pattern.test(str);
}

export { string10to62, validURL };
