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

  it('should call clearTimeout on unmount', async () => {
    jest.useFakeTimers()
    jest.spyOn(global, 'clearInterval');

    const { unmount } = renderHook(() => useInterval(callback, timeout))
    unmount();
    expect(clearInterval).toHaveBeenCalledTimes(1);
  });
})