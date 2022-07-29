import { renderHook } from '@testing-library/react-hooks';
import { useInterval } from './hooks';

describe('Hooks', () => {
  describe('useInterval', () => {
    beforeEach(() => {
      jest.useFakeTimers();
    });

    it('should trigger callback on every interval ms', () => {
      const callback = jest.fn();
      const interval = 100;
      renderHook(() => useInterval(callback, interval));

      jest.advanceTimersByTime(interval);
      expect(callback).toHaveBeenCalledTimes(1);

      jest.advanceTimersByTime(interval);
      expect(callback).toHaveBeenCalledTimes(2);
    });

    it('should trigger new callback instead of previous callback when rerender hook with new callback', () => {
      const oldCallback = jest.fn();
      const interval = 100;
      const { rerender } = renderHook(
        ({ callback, interval }) => useInterval(callback, interval),
        {
          initialProps: {
            callback: oldCallback,
            interval
          }
        }
      );

      jest.advanceTimersByTime(interval);
      expect(oldCallback).toHaveBeenCalledTimes(1);
      oldCallback.mockClear();

      const newCallback = jest.fn();
      rerender({ callback: newCallback, interval });

      jest.advanceTimersByTime(interval);
      expect(oldCallback).not.toHaveBeenCalled();
      expect(newCallback).toHaveBeenCalledTimes(1);
    });

    it('should trigger callback at new interval when interval changes', () => {
      const callback = jest.fn();
      const { rerender } = renderHook(
        ({ callback, interval }) => useInterval(callback, interval),
        {
          initialProps: {
            callback,
            interval: 100
          }
        }
      );

      jest.advanceTimersByTime(100);
      expect(callback).toHaveBeenCalledTimes(1);

      rerender({ callback, interval: 50 });

      jest.advanceTimersByTime(100);
      expect(callback).toHaveBeenCalledTimes(3);
    });
  });
})