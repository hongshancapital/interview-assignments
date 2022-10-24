import eazyThrottle from '../eazyDebounce';

describe('utils', () => {
  it('debounce', (done) => {
    const cb = jest.fn();
    const fn = eazyThrottle(cb);
    let count = 0;
    while(count<10) {
      fn(count);
      count++;
    }
    setTimeout(() => {
      const calls = cb.mock.calls;
      expect(calls.length).toBe(1);
      expect(calls[0][0]).toBe(9);
      done();
    }, 100);
  });
})

