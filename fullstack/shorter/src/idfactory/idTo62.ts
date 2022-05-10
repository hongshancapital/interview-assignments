const characters = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');

function idTo62(num: number): string {
  const len = characters.length;
  const result: Array<string> = [];
  let remainNum = num;
  while (remainNum >= len) {
    const remain = remainNum % len;
    result.unshift(characters[remain]);
    remainNum = Math.floor(remainNum / len);
  }
  result.unshift(characters[remainNum]);
  return result.join('');
}

export { idTo62 };