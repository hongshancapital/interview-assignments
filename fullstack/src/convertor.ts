const map = [
  '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
  'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
  'A', 'B', 'C', 'D', 'E', 'F',
  'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'X',
]

const indexMap = map.reduce(
  (acc, cv, idx) => {
    acc[cv] = idx;
    return acc;
  },
  {} as {[k: string]: number},
)

export function encode(hex: number): string {
  let ret = '';
  let current = hex;
  do {
    const bit = current % 62;
    ret = map[bit] + ret;
    current = Math.floor(current / 62);
  } while (current != 0);
  return ret;
}

export function decode(coding: string): number {
  let hex = 0;
  coding.split('').reverse().forEach(
    (alpha, index) => {
      const bit = indexMap[alpha];
      const multiple = Math.pow(map.length, index);
      hex += bit * multiple;
    }
  );
  return hex;
}
