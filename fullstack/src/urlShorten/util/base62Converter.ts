interface lookupType {
  [key: string]: number;
}

export default class Base62Converter {
  static charset =
    'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789'.split(
      ''
    );
  static base = this.charset.length;

  static _aBeginIndex = 0;
  static _ABeginIndex = 26;
  static _0BeginIndex = 52;

  static lookup = ((): lookupType => {
    const lookupRet: lookupType = this.charset.reduce(
      (acc, cur, idx) => {
        Object.defineProperty(acc, cur, {
          value: cur.charCodeAt(0),
          writable: true
        });

        // acc[cur] = cur.charCodeAt(0);
        return acc;
      },
      {}
    );
    return lookupRet;
  })();

  static _a = this.lookup[this.charset[this._aBeginIndex]];
  static _A = this.lookup[this.charset[this._ABeginIndex]];
  static _0 = this.lookup[this.charset[this._0BeginIndex]];
  static _z = this.lookup['z'];
  static _Z = this.lookup['Z'];
  static _9 = this.lookup['9'];

  static encode(n: number) {
    const url: string[] = [];
    while (n) {
      const _char = this.charset[n % this.base];
      url.push(_char);
      // ~~ 同样作用于负数，在node中不是取整
      n = Math.floor(n / this.base);
    }
    return url.reverse().join('');
  }

  static decode(shortURL: string) {
    return shortURL
      .split('')
      .reduce((acc, cur, idx, arr) => {
        return (
          acc +
          this.charset.indexOf(cur) *
            this.base ** (arr.length - idx - 1)
        );
      }, 0);
  }
}
