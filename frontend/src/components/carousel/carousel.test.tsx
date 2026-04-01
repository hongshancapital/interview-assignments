import React from "react";
import { render, fireEvent, act } from "@testing-library/react";
import Carousel from "./index";

jest.setTimeout(5000);
/**
 * 检测初始状态
 */
test("carousel component load", () => {
  const { container, getByText } = render(
    <Carousel autoplay={false} width={800} height={400}>
      <div>page1</div>
      <div>page2</div>
      <div>page3</div>
    </Carousel>
  );
  // 检验文字
  const textElement = getByText("page1");
  expect(textElement).toBeInTheDocument();
  // 检验按钮
  const banners = container.getElementsByClassName("dot-banner");
  expect(banners).toHaveLength(3);
  // 检验宽高
  const slide = container.getElementsByClassName("slide-wrapper")[0].firstChild;
  expect(slide).toBeInTheDocument();
  if (slide instanceof Element) {
    const { width } = getComputedStyle(slide);
    expect(parseInt(width)).toBe(800);
  } else {
    // 处理 slide 为 null 的情况，进行相应的断言或错误处理
    throw new Error("Slide element is null");
  }
});
/**
 * carousel 测试select功能
 */
test("carousel component select action", () => {
  const { container, getByText } = render(
    <Carousel autoplay={false} width={800} height={400}>
      <div>page1</div>
      <div>page2</div>
      <div>page3</div>
    </Carousel>
  );
  const slidewrapper = container.getElementsByClassName("slide-wrapper")[0];
  const computedStyle0 = getComputedStyle(slidewrapper);
  // 查看初始状态是不是0
  expect(computedStyle0.transform).toBe("translateX(0px)");
  const dots = container.getElementsByClassName("dot");
  const dot1 = dots[0];
  const dot2 = dots[1];
  const dot3 = dots[2];
  const page1Element1 = getByText("page1");
  expect(page1Element1).toBeInTheDocument();
  // 点击第二个按钮
  fireEvent.click(dot2);
  expect(slidewrapper).toBeInTheDocument(); // slidewrapper这个元素是存在的
  const computedStyle2 = getComputedStyle(slidewrapper);
  // 判断值是否正确
  expect(computedStyle2.transform).toBe("translateX(-800px)");
  const page1Element2 = getByText("page2");
  expect(page1Element2).toBeInTheDocument();
  // 点击第三个按钮
  fireEvent.click(dot3);
  const computedStyle3 = getComputedStyle(slidewrapper);
  // 判断值是否正确
  expect(computedStyle3.transform).toBe("translateX(-1600px)");
  const page1Element3 = getByText("page3");
  expect(page1Element3).toBeInTheDocument();
  // 点击第一个按钮
  fireEvent.click(dot1);
  const computedStyle1 = getComputedStyle(slidewrapper);
  // 判断值是否正确
  expect(computedStyle1.transform).toBe("translateX(0px)");
  const page1Element0 = getByText("page1");
  expect(page1Element0).toBeInTheDocument();
});

/**
 * carousel 测试autoplay
 */
test("carousel component autoplay", async () => {
  const { container, getByText } = render(
    <Carousel autoplay={true} width={800} height={400}>
      <div>page1</div>
      <div>page2</div>
      <div>page3</div>
    </Carousel>
  );
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
  }, 1000);
  // 找到slidewrapper组件
  const slidewrapper = container.getElementsByClassName("slide-wrapper")[0];
  const computedStyle1 = getComputedStyle(slidewrapper);
  // 查看初始状态是不是0
  const page1Element1 = getByText("page1");
  expect(page1Element1).toBeInTheDocument();
  expect(computedStyle1.transform).toBe("translateX(0px)");
  await new Promise((resolve) => setTimeout(resolve, 1100));
  const page1Element2 = getByText("page2");
  expect(page1Element2).toBeInTheDocument();
  const computedStyle2 = getComputedStyle(slidewrapper);
  expect(computedStyle2.transform).toBe("translateX(-800px)");
  await new Promise((resolve) => setTimeout(resolve, 1000));
  const page1Element3 = getByText("page3");
  expect(page1Element3).toBeInTheDocument();
  const computedStyle3 = getComputedStyle(slidewrapper);
  expect(computedStyle3.transform).toBe("translateX(-1600px)");
  await new Promise((resolve) => setTimeout(resolve, 1000));
  const page1Element0 = getByText("page1");
  expect(page1Element0).toBeInTheDocument();
  const computedStyle0 = getComputedStyle(slidewrapper);
  expect(computedStyle0.transform).toBe("translateX(0px)");
  // 循环一轮完成测试
});
