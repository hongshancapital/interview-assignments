import { renderHook, act } from '@testing-library/react';
import { useIndex } from './useIndex';

describe('useIndex', () => {
  jest.useFakeTimers();
  it('returns correct index after given duration', () => {
    const { result } = renderHook(() =>
      useIndex({ duration: 1000, childrenLength: 3 })
    );
    act(() => {
      jest.advanceTimersByTime(1000);
    });
    expect(result.current.currIndex).toBe(1);
  });
  it('reset index to 0 if is greater than length', () => {
    const { result } = renderHook(() =>
      useIndex({ duration: 1000, childrenLength: 3 })
    );
    act(() => {
      jest.advanceTimersByTime(3000);
    });
    expect(result.current.currIndex).toBe(0);
  });
});
