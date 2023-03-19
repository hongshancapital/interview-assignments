import { renderHook } from "@testing-library/react";
import { useInterval } from "../hooks/useInterval";

describe("测试useInterval组件", () => {
  let callback: jest.Mock<any, any>;

  beforeEach(() => {
    callback = jest.fn();
    jest.spyOn(global, "setInterval");
    jest.useFakeTimers();
  });

  beforeAll(() => {
    jest.useFakeTimers();
  });

  afterEach(() => {
    callback.mockRestore();
    jest.clearAllTimers();
    jest.restoreAllMocks();
  });

  afterAll(() => {
    jest.useRealTimers();
  });

  test("没有提供延时时间时，不进行interval", () => {
    const { result } = renderHook(() => useInterval(callback));

    expect(result.current).toBeDefined();
    // 没提供delay参数时，默认为null
    expect(setInterval).not.toHaveBeenCalled();
  });

  test("当delay小于0或者不是数字时，不进行interval", () => {
    let delay = -1;
    const { result, rerender } = renderHook(() => useInterval(callback, delay));

    expect(result.current).toBeDefined();
    expect(setInterval).not.toHaveBeenCalled();

    delay = NaN;
    rerender();
    expect(setInterval).not.toHaveBeenCalled();

    delay = Infinity;
    rerender();
    expect(setInterval).not.toHaveBeenCalled();
  });

  test("当delay为null时，不进行interval", () => {
    const { result } = renderHook(() => useInterval(callback, null));

    expect(result.current).toBeDefined();
    // 没提供delay参数时，默认为null
    expect(setInterval).not.toHaveBeenCalled();
  });

  test("当提供了一个delay数字参数时，进行正常的interval", () => {
    renderHook(() => useInterval(callback, 200));

    expect(callback).toHaveBeenCalledTimes(0);
    jest.advanceTimersByTime(200);
    expect(callback).toHaveBeenCalledTimes(1);
    jest.advanceTimersToNextTimer();
    expect(callback).toHaveBeenCalledTimes(2);
  });

  test("在指定的间隔时间内重复触发", () => {
    // jest.restoreAllMocks();
    renderHook(() => useInterval(callback, 200));
    expect(callback).not.toHaveBeenCalled();

    // jest.useFakeTimers();
    jest.advanceTimersByTime(199);
    expect(callback).not.toHaveBeenCalled();

    jest.advanceTimersByTime(1);
    expect(callback).toHaveBeenCalledTimes(1);

    jest.advanceTimersToNextTimer();
    expect(callback).toHaveBeenCalledTimes(2);

    jest.advanceTimersToNextTimer(3);
    expect(callback).toHaveBeenCalledTimes(5);
  });

  test("测试hook卸载后是否清除interval", () => {
    jest.spyOn(global, "clearInterval");
    const { unmount } = renderHook(() => useInterval(callback, 200));
    const initialTimerCount = jest.getTimerCount();
    expect(clearInterval).not.toHaveBeenCalled();

    unmount();

    expect(clearInterval).toHaveBeenCalledTimes(1);
    expect(jest.getTimerCount()).toBe(initialTimerCount - 1);
  });

  test("测试在interval期间更新间隔时间是否工作正常", () => {
    jest.spyOn(global, "clearInterval");
    let delay = 200;
    const { rerender } = renderHook(() => useInterval(callback, delay));
    expect(clearInterval).not.toHaveBeenCalled();

    delay = 500;
    rerender();

    expect(clearInterval).toHaveBeenCalledTimes(1);
    jest.advanceTimersByTime(1000);
    expect(callback).toHaveBeenCalledTimes(2);
  });

  test("测试reset方法是否生效", () => {
    jest.spyOn(global, "clearInterval");
    const { result } = renderHook(() => useInterval(callback, 3000));

    expect(result.current).toBeDefined();
    expect(clearInterval).not.toHaveBeenCalled();
    const [reset] = result.current;

    reset();
    expect(clearInterval).toHaveBeenCalledTimes(1);
    jest.advanceTimersByTime(6000);
    expect(callback).toHaveBeenCalledTimes(2);
  });
});
