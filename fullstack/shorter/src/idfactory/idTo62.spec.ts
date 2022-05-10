import { idTo62 } from './idTo62';

describe('idTo62', () => {
  it('test number 61, 62', () => {
    expect(idTo62(61)).toBe('z');
    expect(idTo62(62)).toBe('10');
    expect(idTo62(7750)).toBe('210');
  });
  it('test random', () => {
    for (let i = 0; i < 10; i++) {
      const x = Math.floor((Math.random() + 0.01) * 10000000);
      const x62 = idTo62(x);
      const characters = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
      let result = 0;
      x62.split('').reverse().forEach((item, index) => {
        result += characters.indexOf(item) * Math.pow(62, index)
      });
      expect(result).toBe(x);
    }
  });
});