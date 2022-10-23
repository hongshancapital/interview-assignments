import { expect } from 'chai';

export const asyncExpect = async (fn: () => Promise<any | void>) => {
  try {
    const result = await fn();

    return expect(result);
  } catch (e) {
    return expect(e);
  }
};
