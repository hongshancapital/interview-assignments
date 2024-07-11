import React from "react";
import { act, renderHook } from "@testing-library/react";
import useInterval from "./useInterval";

describe("useHover测试", () => {
  beforeEach(() => {
    jest.useFakeTimers();
  });

  afterEach(() => {
    jest.useRealTimers();
  });

  it("正确执行对应的次数", () => {
    const fn = jest.fn();
    renderHook(() => useInterval(fn, 1000));
    act(() => {
      jest.advanceTimersByTime(1010);
    });
    expect(fn).toBeCalledTimes(1);
    act(() => {
      jest.advanceTimersByTime(10010);
    });
    expect(fn).toBeCalledTimes(11);
  });

  it("能够暂停", () => {
    const fn = jest.fn();
    const { rerender } = renderHook(
      ({ pause }: any) => useInterval(fn, 1000, { pause }),
      {
        initialProps: {
          pause: false,
        },
      }
    );
    act(() => {
      jest.advanceTimersByTime(1610);
    });
    expect(fn).toBeCalledTimes(1);
    rerender({ pause: true });
    act(() => {
      jest.advanceTimersByTime(10010);
    });
    expect(fn).toBeCalledTimes(1);
    rerender({ pause: false });
    act(() => {
      jest.advanceTimersByTime(400);
    });
    expect(fn).toBeCalledTimes(2);
  });

  it("使用重置函数重置计时", () => {
    const fn = jest.fn();
    const { result, rerender } = renderHook(
      ({ pause }: any) => useInterval(fn, 1000, { pause }),
      {
        initialProps: {
          pause: false,
        },
      }
    );
    act(() => {
      jest.advanceTimersByTime(1010);
    });
    expect(fn).toBeCalledTimes(1);
    act(() => {
      jest.advanceTimersByTime(800);
    });
    expect(fn).toBeCalledTimes(1);
    act(() => {
      result.current();
    });

    rerender({ pause: false });
    act(() => {
      jest.advanceTimersByTime(550);
    });
    expect(fn).toBeCalledTimes(1);
    act(() => {
      jest.advanceTimersByTime(500);
    });
    expect(fn).toBeCalledTimes(2);
  });

  it("多次暂停能够正常计时", () => {
    const fn = jest.fn();
    const { rerender } = renderHook(
      ({ pause }: any) => useInterval(fn, 1000, { pause }),
      {
        initialProps: {
          pause: false,
        },
      }
    );
    act(() => {
      jest.advanceTimersByTime(1010);
    });
    expect(fn).toBeCalledTimes(1);
    rerender({ pause: true });
    act(() => {
      jest.advanceTimersByTime(10010);
    });
    expect(fn).toBeCalledTimes(1);
    rerender({ pause: false });
    expect(fn).toBeCalledTimes(1);
    act(() => {
      jest.advanceTimersByTime(400);
    });
    expect(fn).toBeCalledTimes(1);
    rerender({ pause: true });
    act(() => {
      jest.advanceTimersByTime(1000);
    });
    expect(fn).toBeCalledTimes(1);
    rerender({ pause: false });
    act(() => {
      jest.advanceTimersByTime(400);
    });
    expect(fn).toBeCalledTimes(1);
    act(() => {
      jest.advanceTimersByTime(200);
    });
    expect(fn).toBeCalledTimes(2);
  });
});
