import { renderHook } from '@testing-library/react-hooks';
import { useCarouselActiveIndex } from './useCarouselActiveIndex';
import { act } from '@testing-library/react';

describe('useCarouselActiveIndex', () => {
  beforeEach(() => {
    jest.useFakeTimers();
  });

  it('should return 0 by default', () => {
    const { result } = renderHook(() => useCarouselActiveIndex(2, 3000));
    expect(result.current).toBe(0);
  });

  it('should return 1 after duration ms', () => {
    const { result } = renderHook(() => useCarouselActiveIndex(2, 3000));

    act(() => {
      jest.advanceTimersByTime(3000);
    })

    expect(result.current).toBe(1);
  });

  it('should return 0 after duration ms when current active index is the last one', () => {
    const { result } = renderHook(() => useCarouselActiveIndex(3, 3000, 2));

    act(() => {
      jest.advanceTimersByTime(3000);
    })

    expect(result.current).toBe(0);
  });
});