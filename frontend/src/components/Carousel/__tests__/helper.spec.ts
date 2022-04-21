import { indexToState, getTranslateX } from '../helper';

describe('Helper utils', () => {
  describe('indexToState function', () => {
    it('Should return "prev" when index is less than currentIndex', () => {
      expect(indexToState(0, 1)).toBe('prev');
    });

    it('Should return "next" when index is greater than currentIndex', () => {
      expect(indexToState(2, 1)).toBe('next');
    });

    it('Should return "current" when index is equal to currentIndex', () => {
      expect(indexToState(1, 1)).toBe('current');
    });
  });

  describe('getTranslateX function', () => {
    it('Should return "-100%" when state is "prev"', () => {
      expect(getTranslateX('prev')).toBe('-100%');
    });

    it('Should return "100%" when state is "next"', () => {
      expect(getTranslateX('next')).toBe('100%');
    });

    it('Should return "0" when state is "current"', () => {
      expect(getTranslateX('current')).toBe('0');
    });
  });
});
