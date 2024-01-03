export const Base62 = {
  charset: '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ',
  encode(input: bigint) {
    if (input === 0n) return '0';
    let s = '';
    while (input > 0n) {
      s = this.charset[Number(input % 62n)] + s;
      input = input / 62n;
    }
    return s;
  },
  decode(input: string) {
    let i = 0n;
    for (let char of input) {
      i = i * 62n + BigInt(this.charset.indexOf(char));
    }
    return i;
  },
};

export const Encrypt = {
  secretKey: BigInt(0xbdf6457d),
  encrypt(num: bigint) {
    return num + this.secretKey;
  },
  decrypt(num: bigint) {
    return num - this.secretKey;
  },
};
