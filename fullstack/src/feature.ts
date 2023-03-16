import { getStore } from "./store";
import crypto from 'crypto';
import { encode } from "./convertor";

let uniqueId = 0;

export function toShort(link: string) {
  const cache = getStore().long2short(link);
  if (cache) return cache;

  const signature = crypto.createHash('md5').update(link).digest('hex');
  const doubleSign = `${signature}${signature}`;

  for (let index = 0; index < signature.length; index++) {
    const hexString = doubleSign.slice(index, index + 11);
    const hex = parseInt(hexString, 16);
    const short = encode(hex);
    const exists = getStore().short2long(short);
    if (!exists) {
      getStore().save(link, short);
      return short;
    }
  }

  while (true) {
    const hex = uniqueId ++;
    const short = encode(hex);
    const exists = getStore().short2long(short);
    if (!exists) {
      getStore().save(link, short);
      return short;
    }
  }
}

export function toLong(short: string) {
  return getStore().short2long(short);
}
