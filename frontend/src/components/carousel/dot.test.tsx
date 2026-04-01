import React from "react";
import { render, fireEvent, act } from "@testing-library/react";
import Dot from "./dot";

jest.setTimeout(2000);
/**
 * 检测选中状态是否正确
 */
test("dot component select test", () => {
  const { container } = render(
    <Dot
      index={1}
      currentIndex={1}
      autoplay={false}
      onSelect={(num: number) => {}}
    />
  );
  // 检测width是不是100%
  const banner = container.getElementsByClassName("dot-banner")[0];
  const style = getComputedStyle(banner);
  expect(style.width).toBe("100%");
});
/**
 * 检测选中状态是否正确
 */
test("dot component no select test", () => {
  const { container } = render(
    <Dot
      index={1}
      currentIndex={2}
      autoplay={true}
      onSelect={(num: number) => {}}
    />
  );
  const banner = container.getElementsByClassName("dot-banner")[0];
  const style = getComputedStyle(banner);
  // 查看初始状态是不是0
  expect(style.width).toBe("0px");
});

/**
 * 验证autoplay的状态
 */
test("dot component autoplay select test", () => {
  const { container } = render(
    <Dot
      index={1}
      currentIndex={1}
      autoplay={true}
      onSelect={(num: number) => {}}
    />
  );
  const banner = container.getElementsByClassName("dot-banner")[0];
  const style = getComputedStyle(banner);
  // 检测是否挂上了autoplay 状态
  expect(style.animation).toBe("carousel-auto-banner 3000ms ease-out");
});

/**
 * 验证click 是否正确的状态
 */
test("dot component click test", () => {
  const { container } = render(
    <Dot
      index={1}
      currentIndex={3}
      autoplay={true}
      onSelect={(num: number) => {
        // 点击跳转到当前的index
        expect(num).toBe(1);
      }}
    />
  );
  // 出发click
  const dot = container.getElementsByClassName("dot-li")[0];
  fireEvent.click(dot);
});

/**
 * 验证next状态跳转是否正确
 */
test("dot component next action test", () => {
  const { container } = render(
    <Dot
      index={0}
      currentIndex={3}
      autoplay={true}
      onSelect={(num: number) => {
        // 从0 跳转到1
        expect(num).toBe(1);
      }}
    />
  );
  const banner = container.getElementsByClassName("dot-banner")[0];
  // 模拟animationend事件
  setTimeout(() => {
    act(() => {
      const event = new Event("animationend", { bubbles: true });
      banner.dispatchEvent(event);
    });
  }, 1000);
});
