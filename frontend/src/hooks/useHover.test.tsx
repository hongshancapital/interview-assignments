import React from "react";
import { render, renderHook } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import useHover from "./useHover";

describe("useHover测试", () => {
  it("元素找不到时不报错", () => {
    const { result } = renderHook(() => useHover(null));
    expect(result.current).toBe(false);
  });

  it("hover时状态为true,取消hover状态为false", () => {
    const { getByText } = render(<div className="hover">testHover</div>);
    let { result, rerender } = renderHook(() =>
      useHover(getByText(/testHover/i))
    );
    expect(result.current).toBe(false);
    userEvent.hover(document.querySelector(".hover") as Element);
    rerender();
    expect(result.current).toBe(true);
    userEvent.unhover(document.querySelector(".hover") as Element);
    rerender();
    expect(result.current).toBe(false);
  });

  it("onEnter和onLeave会被正确调用", () => {
    const { getByText } = render(<div className="hover">testHover</div>);
    const enter = jest.fn();
    const leave = jest.fn();
    let { rerender } = renderHook(() =>
      useHover(getByText(/testHover/i), {
        onEnter: enter,
        onLeave: leave,
      })
    );
    userEvent.hover(document.querySelector(".hover") as Element);
    rerender();
    expect(enter).toHaveBeenCalled();
    expect(leave).not.toHaveBeenCalled();
    enter.mockClear();
    userEvent.unhover(document.querySelector(".hover") as Element);
    rerender();
    expect(enter).not.toHaveBeenCalled();
    expect(leave).toHaveBeenCalled();
  });
});
