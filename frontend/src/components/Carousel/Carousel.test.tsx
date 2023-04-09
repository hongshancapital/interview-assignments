import { render, act, fireEvent } from "@testing-library/react";
import React, { createRef } from "react";
import Carousel from "./index";
import type { CarouselRef } from "./index";

describe("Carousel", () => {
  // 1. 无 children 传入时，组件不渲染
  test("loads and displays carousel without data", () => {
    const { container } = render(<Carousel></Carousel>);
    expect(container.querySelectorAll(".carousel-container").length).toBe(0);
  });

  // 2. ref 提供 activeIndex, setActiveIndex
  test("should has ref", () => {
    const ref = createRef<CarouselRef>();
    render(
      <Carousel ref={ref}>
        <div className="test-child">dom1</div>
        <div className="test-child">dom2</div>
      </Carousel>
    );
    const { activeIndex, setActiveIndex } = ref.current || {};

    expect(typeof activeIndex).toBe("number");
    expect(typeof setActiveIndex).toBe("function");

    // 测试 setActiveIndex
    expect(ref.current?.activeIndex).toBe(0);
    act(() => {
      ref.current?.setActiveIndex(1);
    });
    expect(ref.current?.activeIndex).toBe(1);
  });

  // 3. 测试 children 动态渲染
  test("test async data", () => {
    jest.useFakeTimers();
    const { container, rerender } = render(
      <Carousel>
        {["1"].map((item) => (
          <span key={item}>{item}</span>
        ))}
      </Carousel>
    );
    expect(container.querySelectorAll(".slider-list-item").length).toBe(1);

    act(() => {
      jest.advanceTimersByTime(1000);
    });

    rerender(
      <Carousel>
        {["1", "2", "3", "4", "5"].map((item) => (
          <span key={item}>{item}</span>
        ))}
      </Carousel>
    );
    expect(container.querySelectorAll(".slider-list-item").length).toBe(5);
  });

  // 4. 测试交互
  test("test interaction", async () => {
    jest.useFakeTimers();

    const INTERVAL = 2000;
    const DURATION = 2;
    const ref = createRef<CarouselRef>();
    const { container } = render(
      <Carousel ref={ref} interval={INTERVAL} duration={DURATION}>
        <div className="test-child">dom1</div>
        <div className="test-child">dom2</div>
        <div className="test-child">dom3</div>
        <div className="test-child">dom4</div>
        <div className="test-child">dom5</div>
      </Carousel>
    );

    const listNode = container.querySelector(".slider-list");
    const childNodeList = container.querySelectorAll(".test-child");
    const childrenCount = childNodeList.length;
    const itemWidth = (1 / childrenCount) * 100;

    /** 3.1 测试初始化流程 */

    // 确保轮播容器的 width、transition、transform 计算符合预期
    expect(listNode).toHaveStyle({
      width: `${childrenCount * 100}%`,
      transition: `transform ${DURATION}s`,
    });

    childNodeList.forEach((item) => {
      // 确保每个子项被自动注入 slider-list-item 的 className
      expect(item).toHaveClass("slider-list-item");
      // 确保每个子项 width = (1 / childrenCount) * 100
      expect(item).toHaveStyle({
        width: `${itemWidth}%`,
      });
    });

    // 封装 activeIndex 如期更新时对应的 dom 变化，targetIndex：预期值
    const checkActiveDom = (targetIndex: number) => {
      // （1）校验 activeIndex 的值是否符合预期
      expect(ref.current?.activeIndex).toBe(targetIndex);
      // （2）轮播容器的 transform translateX 值是否符合预期
      expect(listNode).toHaveStyle({
        transform:
          targetIndex >= 0 ? `translateX(${-itemWidth * targetIndex}%)` : "",
      });
      // （3）校验对应的 dot 进度条是否高亮
      expect(
        container.querySelectorAll(".slider-dot-progress")[targetIndex]
      ).toHaveStyle({
        animationName: "progress",
      });
    };

    /** 3.2 测试自动轮播 */

    // 自动轮播两圈
    for (let i = 0; i < childrenCount * 2; i++) {
      // 预期活动单元 index = i % childrenCount
      checkActiveDom(i % childrenCount);
      // 间隔 INTERVAL 后执行下一轮播放
      act(() => {
        jest.advanceTimersByTime(INTERVAL);
      });
    }

    /** 3.3 测试点击进度条的切换行为 */

    // 随机生成一个 childrenCount 以内的 index
    const index = Math.ceil(Math.random() * (childrenCount - 1));
    // 测试点击
    fireEvent.click(container.querySelectorAll(".slider-dot-item")[index]);
    // 预期活动单元 index 变为 seq
    checkActiveDom(index);
  });
});
