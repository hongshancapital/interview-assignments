import Base62 from '../Base62';

/**
 * @author thornwang
 * @priority P0
 * @casetype unit
 */
describe('Base62/toBase10', () => {
  it('when base62 is abc, result should be 39134', () => {
    expect(Base62.toBase10('abc')).toBe(39134);
  });

  it('when base62 is "", result should be 0', () => {
    expect(Base62.toBase10('')).toBe(0);
  });
});

/**
 * @author thornwang
 * @priority P0
 * @casetype unit
 */
describe('Base62/toBase62', () => {
  it('when base10 is 1234567, result should be 5ban', () => {
    expect(Base62.toBase62('1234567')).toBe('5ban');
  });

  it('when base10 is 0, result should be 0', () => {
    expect(Base62.toBase62(0)).toBe('0');
  });
});