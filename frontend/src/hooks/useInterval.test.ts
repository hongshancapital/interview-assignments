import { renderHook } from '@testing-library/react-hooks';
import useInterval from './useInterval';

let callback: jest.Mock;

beforeEach(() => {
  callback = jest.fn();
  jest.useFakeTimers();
});

afterEach(() => {
  callback.mockRestore();
  jest.clearAllMocks();
  jest.clearAllTimers();
});

afterAll(() => {
  jest.useRealTimers();
});

it('should repeatedly calls provided callback with a fixed time delay between each call', () => {
  renderHook(() => useInterval(callback, 200));
  expect(callback).not.toHaveBeenCalled();

  // fast-forward time until 1s before it should be executed
  jest.advanceTimersByTime(199);
  expect(callback).not.toHaveBeenCalled();

  // fast-forward until 1st call should be executed
  jest.advanceTimersByTime(1);
  expect(callback).toHaveBeenCalledTimes(1);

  // fast-forward until next timer should be executed
  jest.advanceTimersToNextTimer();
  expect(callback).toHaveBeenCalledTimes(2);

  // fast-forward until 3 more timers should be executed
  jest.advanceTimersToNextTimer(3);
  expect(callback).toHaveBeenCalledTimes(5);
});

it('should clear interval on unmount', () => {
  const spyFunc = jest.spyOn(window, 'clearInterval')
  const { unmount } = renderHook(() => useInterval(callback, 200));
  const initialTimerCount = jest.getTimerCount();
  expect(spyFunc).not.toHaveBeenCalled();

  unmount();

  expect(spyFunc).toHaveBeenCalledTimes(1);
  expect(jest.getTimerCount()).toBe(initialTimerCount - 1);
});

it('should init hook without delay', () => {
  const spyFunc = jest.spyOn(window, 'setInterval')
  const { result } = renderHook(() => useInterval(callback, null));

  expect(result.current).toBeUndefined();
  // if null delay provided, it's assumed as no delay
  expect(spyFunc).not.toHaveBeenCalled();
});
