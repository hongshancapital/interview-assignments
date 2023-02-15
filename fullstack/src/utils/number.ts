const VALID_CHARS = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';

const VALID_CHARS_LEN = VALID_CHARS.length;

const STR_CACHE: bigint[] = [BigInt(1)];

let i = 1;

while (i < 13) {
  STR_CACHE.push(STR_CACHE[i - 1] * BigInt(VALID_CHARS_LEN));
  i++;
}

function getRemaindNumber(num: number, base: number) {
  return num % base;
}

function getTranslateNumber(num: number, remaind: number, base: number) {
  return (num - remaind) / base;
}

function getRemaindBigInt(num: bigint, base: number): number {
  return Number(num % BigInt(base));
}

function getTranslateBigInt(num: bigint, remaind: number, base: number): bigint {
  return (num - BigInt(remaind)) / BigInt(base);
}

export interface PaddingOption {
  pos: 'left' | 'right' | '' | undefined;
  char: string;
  len: number;
}

export function idToStr(
  num: bigint | number,
  padding: PaddingOption = {
    pos: '',
    char: '0',
    len: -1,
  },
): string {
  const res = [];

  if (typeof num === 'number') {
    let _num = num;

    while (_num !== 0) {
      const t = getRemaindNumber(_num, VALID_CHARS_LEN);

      res.unshift(VALID_CHARS[t]);
      _num = getTranslateNumber(_num, t, VALID_CHARS_LEN);
    }
  } else {
    let _num = num;

    while (_num !== BigInt(0)) {
      const t = getRemaindBigInt(_num, VALID_CHARS_LEN);

      res.unshift(VALID_CHARS[t]);
      _num = getTranslateBigInt(_num, t, VALID_CHARS_LEN);
    }
  }
  if (padding && padding.pos) {
    if (padding.pos === 'left') {
      padding.len > res.length &&
        res.unshift(...Array(padding.len - res.length).fill(padding.char));
    } else {
      padding.len > res.length && res.push(...Array(padding.len - res.length).fill(padding.char));
    }
  }
  return res.join('');
}

export function strToId(str: string): bigint {
  if (str.length > STR_CACHE.length) {
    throw new Error('not support string');
  }
  const data = str.split('').map((c) => {
    const index = VALID_CHARS.indexOf(c);

    if (index <= -1) {
      throw new Error('not support string');
    }
    return index;
  });

  return data.reduceRight(
    (pre, cur, index) => pre + STR_CACHE[data.length - 1 - index] * BigInt(cur),
    BigInt(0),
  );
}
