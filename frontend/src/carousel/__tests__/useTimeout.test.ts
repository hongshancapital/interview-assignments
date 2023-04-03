/**
 * useTimeout应当被视为一个通用hook，而非progressbars的代码片段
 * 故而提供独立单元测试
 */
import { renderHook, act } from '@testing-library/react';
import { useTimeout } from '../useTimeout';

jest.useFakeTimers();

test('useTimeout，根据时间判断进度和callback执行情况', () => {
  const callback = jest.fn()
  const { result } = renderHook(() => useTimeout(callback, 3000))
  act(() => {
    jest.advanceTimersByTime(1000);
  })
  expect(result.current).toBeCloseTo(0.333)
  act(() => {
    jest.advanceTimersByTime(1000);
  })
  expect(result.current).toBeCloseTo(0.666)
  expect(callback).toHaveBeenCalledTimes(0);
  act(() => {
    jest.runAllTimers()
  })
  expect(callback).toHaveBeenCalledTimes(1);
  expect(jest.getTimerCount()).toBe(0)
  expect(result.current).toBeCloseTo(1)
});
