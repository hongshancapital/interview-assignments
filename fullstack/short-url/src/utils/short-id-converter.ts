import Hashids from 'hashids';

export type NumberId = number;
export type HashId = string;

const base58alphabet = '123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz';

const hashids = new Hashids('short-url', 8, base58alphabet);

export const encode = (id: NumberId): HashId => hashids.encode(id);
export const decode = (hashId: HashId): NumberId => {
  const result = hashids.decode(hashId);
  if (result.length !== 1) {
    throw new Error('No Result');
  }
  const numberId = result[0];
  if (typeof numberId !== 'number') {
    throw new Error('Result Not Number');
  }

  return numberId;
};
