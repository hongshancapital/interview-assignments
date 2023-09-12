import React from "react";
import { render, fireEvent, act } from "@testing-library/react";
import App from "./App";

jest.setTimeout(6000);
/**
 * 初始化是否正确是否正确
 */
test("app renders load", () => {
  const { getByText, container } = render(<App />);
  const textElement = getByText("xPhone");
  expect(textElement).toBeInTheDocument();
  const dots = container.getElementsByClassName("dot");
  expect(dots).toHaveLength(3);
});

/**
 * 选择slide是否正确
 */
test("app select slide", () => {
  global.window.innerWidth = 800;
  global.window.innerHeight = 500;
  const { container } = render(<App />);
  // 找到slidewrapper组件
  const slidewrapper = container.getElementsByClassName("slide-wrapper")[0];
  const computedStyle0 = getComputedStyle(slidewrapper);
  // 查看初始状态是不是0
  expect(computedStyle0.transform).toBe("translateX(0px)");
  const dots = container.getElementsByClassName("dot");
  const dot1 = dots[0];
  const dot2 = dots[1];
  const dot3 = dots[2];
  // 点击第二个按钮
  fireEvent.click(dot2);
  expect(slidewrapper).toBeInTheDocument(); // slidewrapper这个元素是存在的
  const computedStyle2 = getComputedStyle(slidewrapper);
  // 判断值是否正确
  expect(computedStyle2.transform).toBe("translateX(-800px)");
  // 点击第三个按钮
  fireEvent.click(dot3);
  const computedStyle3 = getComputedStyle(slidewrapper);
  // 判断值是否正确
  expect(computedStyle3.transform).toBe("translateX(-1600px)");
  // 点击第一个按钮
  fireEvent.click(dot1);
  const computedStyle1 = getComputedStyle(slidewrapper);
  // 判断值是否正确
  expect(computedStyle1.transform).toBe("translateX(0px)");
});

/**
 * autoplay是否正确
 * 这里可以用 playwright 或者是 puppeteer 这样的无头浏览器来测试
 */
test("app  autoplay", async () => {
  global.window.innerWidth = 800;
  global.window.innerHeight = 500;
  const { container } = render(<App />);
  // 找到dot-banner组件
  const banners = container.getElementsByClassName("dot-banner");
  expect(banners).toHaveLength(3);
  let next = 0;
  //这里模拟轮训事件分别在banner组件上触发
  setInterval(() => {
    act(() => {
      const event = new Event("animationend", { bubbles: true });
      banners[next].dispatchEvent(event);
      next = (next + 1) % 3;
    });
  }, 500);
  // 找到slidewrapper组件
  const slidewrapper = container.getElementsByClassName("slide-wrapper")[0];
  const computedStyle1 = getComputedStyle(slidewrapper);
  // 查看初始状态是不是0
  expect(computedStyle1.transform).toBe("translateX(0px)");
  await new Promise((resolve) => setTimeout(resolve, 600));
  const computedStyle2 = getComputedStyle(slidewrapper);
  expect(computedStyle2.transform).toBe("translateX(-800px)");
  await new Promise((resolve) => setTimeout(resolve, 500));
  const computedStyle3 = getComputedStyle(slidewrapper);
  expect(computedStyle3.transform).toBe("translateX(-1600px)");
  await new Promise((resolve) => setTimeout(resolve, 500));
  const computedStyle0 = getComputedStyle(slidewrapper);
  expect(computedStyle0.transform).toBe("translateX(0px)");
  // 循环一轮完成测试
});

/**
 * 窗口重新resize
 */
test("app window resize", async () => {
  global.window.innerWidth = 800;
  global.window.innerHeight = 500;
  const { container } = render(<App />);
  const carousel = container.getElementsByClassName("carousel")[0];
  expect(carousel).toBeInTheDocument();
  const { width: width1, height: height1 } = getComputedStyle(carousel);
  expect(parseInt(width1)).toBe(800);
  expect(parseInt(height1)).toBe(500);
  // 改变size
  act(() => {
    window.innerWidth = 500;
    window.innerHeight = 400;
    const resizeEvent = new window.Event("resize");
    window.dispatchEvent(resizeEvent);
  });
  await new Promise((resolve) => setTimeout(resolve, 100));
  const { width: width2, height: height2 } = getComputedStyle(carousel);
  expect(parseInt(width2)).toBe(500);
  expect(parseInt(height2)).toBe(400);
  // 改变size
  act(() => {
    window.innerWidth = 200;
    window.innerHeight = 200;
    const resizeEvent = new window.Event("resize");
    window.dispatchEvent(resizeEvent);
  });
  await new Promise((resolve) => setTimeout(resolve, 100));
  const { width: width3, height: height3 } = getComputedStyle(carousel);
  expect(parseInt(width3)).toBe(200);
  expect(parseInt(height3)).toBe(200);
  // 改变size
  act(() => {
    window.innerWidth = 1000;
    window.innerHeight = 600;
    const resizeEvent = new window.Event("resize");
    window.dispatchEvent(resizeEvent);
  });
  await new Promise((resolve) => setTimeout(resolve, 100));
  const { width: width4, height: height4 } = getComputedStyle(carousel);
  expect(parseInt(width4)).toBe(1000);
  expect(parseInt(height4)).toBe(600);
});
