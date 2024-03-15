import { act, renderHook } from '@testing-library/react';
import { useIndex } from './useIndex';

describe('useIndex', () => {
  beforeAll(() => {
    jest.useFakeTimers();
  });

  it('returns correct index after given duration', () => {
    const { result } = renderHook(() =>
      useIndex({ duration: 3000, childrenLength: 3 })
    );

    act(() => {
      jest.advanceTimersByTime(3000);
    });

    expect(result.current).toBe(1);
  });

  it('returns 0 if index is equal childrenLength', () => {
    const { result } = renderHook(() =>
      useIndex({ duration: 3000, childrenLength: 3 })
    );

    act(() => {
      jest.advanceTimersByTime(9000);
    });

    expect(result.current).toBe(0);
  });
});
