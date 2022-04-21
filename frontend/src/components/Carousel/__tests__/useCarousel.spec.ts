import { renderHook, act } from '@testing-library/react-hooks';

import { useCarousel } from '../useCarousel';

describe('useCarousel', () => {
  jest.useFakeTimers();

  const duration = 1000;

  describe('When starting', () => {
    it('"currentIndex" should be 0', () => {
      const { result } = renderHook(() => useCarousel(3, duration));

      expect(result.current.currentIndex).toBe(0);
      expect(result.current.transitionEndIndex).toBe(0);
    });
  });

  describe('When time elapses 1 times the duration', () => {
    it('"currentIndex" should be 1', () => {
      const { result } = renderHook(() => useCarousel(3, duration));

      act(() => {
        jest.advanceTimersByTime(duration);
      });

      expect(result.current.currentIndex).toBe(1);
    });
  });

  describe('When time elapses 2 times the duration', () => {
    it('"currentIndex" should be 2', () => {
      const { result } = renderHook(() => useCarousel(3, duration));

      act(() => {
        jest.advanceTimersByTime(duration);
      });

      expect(result.current.currentIndex).toBe(1);

      act(() => {
        result.current.setTransitionEndIndex(1);
      });

      act(() => {
        jest.advanceTimersByTime(duration);
      });

      expect(result.current.currentIndex).toBe(2);
    });
  });

  describe('When time elapses 3 times the duration', () => {
    it('"currentIndex" should be 0', () => {
      const { result } = renderHook(() => useCarousel(3, duration));

      act(() => {
        jest.advanceTimersByTime(duration);
      });

      expect(result.current.currentIndex).toBe(1);

      act(() => {
        result.current.setTransitionEndIndex(1);
      });

      act(() => {
        jest.advanceTimersByTime(duration);
      });

      expect(result.current.currentIndex).toBe(2);
      expect(result.current.transitionEndIndex).toBe(1);

      act(() => {
        result.current.setTransitionEndIndex(2);
      });

      act(() => {
        jest.advanceTimersByTime(duration);
      });

      expect(result.current.currentIndex).toBe(0);
      expect(result.current.transitionEndIndex).toBe(2);
    });
  });

  describe('When time passes 1/2 of the duration, and jump to index 2', () => {
    it('"currentIndex" should be 2', () => {
      const { result } = renderHook(() => useCarousel(3, duration));

      expect(result.current.currentIndex).toBe(0);
      expect(result.current.transitionEndIndex).toBe(0);

      act(() => {
        jest.advanceTimersByTime(duration / 2);
      });

      expect(result.current.currentIndex).toBe(0);

      act(() => {
        result.current.jumpTo(2);
      });

      expect(result.current.currentIndex).toBe(2);

      act(() => {
        result.current.setTransitionEndIndex(2);
      });

      act(() => {
        jest.advanceTimersByTime(duration / 2);
      });

      expect(result.current.currentIndex).toBe(2);

      act(() => {
        jest.advanceTimersByTime(duration / 2);
      });

      expect(result.current.currentIndex).toBe(0);
    });
  });
});
