import { describe, expect, it } from '@jest/globals';
import { Base62, Encrypt } from './utils';

describe('utils', () => {
  it('Encrypt', () => {
    expect(Encrypt.decrypt(Encrypt.encrypt(1n))).toEqual(1n);
  });

  it('Base62', () => {
    expect(Base62.encode(100n)).toEqual('1C');
    expect(Base62.decode('1C')).toEqual(100n);
    expect(Base62.encode(0n)).toEqual('0');
  });
});
