import { renderHook, act } from '@testing-library/react';
import { useCarousel } from '../useCarousel';

describe('useCarousel', () => {
  beforeEach(() => {
    jest.useFakeTimers();
  });

  afterEach(() => {
    jest.useRealTimers();
  });

  it('should get an index of 0 after render', () => {
    const { result } = renderHook(() =>
      useCarousel({ duration: 1000, total: 3 })
    );
    expect(result.current.currentIndex).toBe(0);
  });

  it('should loop get the next index at intervals', () => {
    const { result } = renderHook(() =>
      useCarousel({ duration: 1000, total: 3 })
    );
    expect(result.current.currentIndex).toBe(0);
    act(() => {
      jest.advanceTimersByTime(1000);
    });
    expect(result.current.currentIndex).toBe(1);
    act(() => {
      jest.advanceTimersByTime(2000);
    });
    expect(result.current.currentIndex).toBe(0);
  });
});
