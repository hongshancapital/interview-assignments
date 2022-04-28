import { renderHook, act } from '@testing-library/react-hooks';
import useInterval from './useInterval';

let callback = jest.fn();
const sleep = (ms: number) => new Promise(resolve => setTimeout(resolve, ms));
const timeout = 500;
const earlyTimeout = 400;

describe('useInterval()', () => {
  it('should fire the callback function (1)', async () => {

    const { result } = renderHook(() => useInterval(callback, timeout));
    expect(result.current).toBeDefined();
    await sleep(timeout);
    expect(callback).toHaveBeenCalledTimes(1)
  });

  it('should not fire the callback when in early time', async () => {
    const { result } = renderHook(() => useInterval(callback, timeout));
    act(() => {
      result.current.toggleIntervalSwitch(false);
    });
    expect(result.current).toBeDefined();
    await sleep(earlyTimeout);
    expect(callback).not.toHaveBeenCalled()
  });

  it('should not fire the callback when switchOn is false', async () => {
    const switchOn = false;
    const { result } = renderHook(() => useInterval(callback, timeout, switchOn));

    expect(result.current).toBeDefined();
    await sleep(timeout);
    expect(callback).not.toHaveBeenCalled()
  });

  it('should callback occur on a case-by-case basis, when using toggleIntervalSwitch', async () => {
    const timeout = 500
    const { result } = renderHook(() => useInterval(callback, timeout));
    act(() => {
      result.current.toggleIntervalSwitch(false);
    })
    await sleep(timeout);
    expect(callback).not.toHaveBeenCalled();
    act(() => {
      result.current.toggleIntervalSwitch(true);
    })
    await sleep(timeout);
    expect(callback).toHaveBeenCalledTimes(1)
    await sleep(timeout);
    expect(callback).toHaveBeenCalledTimes(2)
  });

  it('should call clearTimeout on unmount', async () => {
    jest.useFakeTimers()
    jest.spyOn(global, 'clearInterval');
    const { unmount } = renderHook(() => useInterval(callback, timeout))
    unmount();
    expect(clearInterval).toHaveBeenCalledTimes(1);
  });
})