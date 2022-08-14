import { cx, useScreen, useUpdate } from './common';
import { renderHook } from '@testing-library/react-hooks';

test('create classes by cx', () => {
  expect(cx('a', false, 'b', undefined)).toBe('a b');
});

test('invoke function by using useUpdate', () => {
  let dep = 0;
  const fn = jest.fn();
  const { rerender } = renderHook(() => useUpdate(fn, [dep]));
  expect(fn).not.toBeCalled();
  rerender();
  expect(fn).not.toBeCalled();
  dep = 1;
  rerender();
  expect(fn).toHaveBeenCalled();
  dep = 2;
  rerender();
  expect(fn).toHaveBeenCalledTimes(2);
});

test('current window size', () => {
  const { result, rerender } = renderHook(() => useScreen())
  expect(result.current[0]).toBe(window.innerWidth);
  expect(result.current[1]).toBe(window.innerHeight);
  rerender();
  expect(result.current[0]).toBe(window.innerWidth);
  expect(result.current[1]).toBe(window.innerHeight);
});