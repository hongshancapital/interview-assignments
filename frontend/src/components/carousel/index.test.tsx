import React from "react";
import { act, render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import Carousel from ".";

describe("Carousel测试", () => {
  beforeEach(() => {
    jest.useFakeTimers();
  });

  afterEach(() => {
    jest.useRealTimers();
  });

  it("空子元素的情况下隐藏", () => {
    const { baseElement } = render(<Carousel></Carousel>);
    expect(baseElement.getBoundingClientRect().height).toBe(0);
  });

  it("初始状态下偏移为零", () => {
    const { container } = render(
      <Carousel>
        <div>1</div>
        <div>2</div>
      </Carousel>
    );
    expect(container.querySelector(".list")?.getAttribute("style")).toBe(
      "transform: translateX(-0%);"
    );
  });

  it("经过指定时间后移到下一项", () => {
    const { container } = render(
      <Carousel duration={1000}>
        <div>1</div>
        <div>2</div>
      </Carousel>
    );
    act(() => {
      jest.advanceTimersByTime(1100);
    });
    expect(container.querySelector(".list")?.getAttribute("style")).toBe(
      "transform: translateX(-100%);"
    );
  });

  it("两个元素时，经过两个时间间隔后移到初始位置", () => {
    const { container } = render(
      <Carousel duration={1000}>
        <div>1</div>
        <div>2</div>
      </Carousel>
    );
    act(() => {
      jest.advanceTimersByTime(2100);
    });
    expect(container.querySelector(".list")?.getAttribute("style")).toBe(
      "transform: translateX(-0%);"
    );
  });

  it("点击进度条，移动到相应的位置", async () => {
    const { container } = render(
      <Carousel>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    );
    await userEvent.click(document.querySelectorAll(".dot")[2]);
    expect(container.querySelector(".list")?.getAttribute("style")).toBe(
      "transform: translateX(-200%);"
    );
  });

  it("点击进度条当前点，轮播图不受影响", async () => {
    const { container } = render(
      <Carousel duration={1000}>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    );
    act(() => {
      jest.advanceTimersByTime(1100);
    });
    expect(container.querySelector(".list")?.getAttribute("style")).toBe(
      "transform: translateX(-100%);"
    );
    await userEvent.click(document.querySelectorAll(".dot")[1]);
    expect(container.querySelector(".list")?.getAttribute("style")).toBe(
      "transform: translateX(-100%);"
    );
  });

  it("hover时暂停切换，取消hover后继续切换", async () => {
    const { container } = render(
      <Carousel duration={1000}>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    );
    act(() => {
      jest.advanceTimersByTime(1100);
    });
    expect(container.querySelector(".list")?.getAttribute("style")).toBe(
      "transform: translateX(-100%);"
    );
    await userEvent.hover(document.querySelector(".list") as Element);
    act(() => {
      jest.advanceTimersByTime(1100);
    });
    expect(container.querySelector(".list")?.getAttribute("style")).toBe(
      "transform: translateX(-100%);"
    );
    await userEvent.unhover(document.querySelector(".list") as Element);
    act(() => {
      jest.advanceTimersByTime(1100);
    });
    expect(container.querySelector(".list")?.getAttribute("style")).toBe(
      "transform: translateX(-200%);"
    );
  });

  it("短暂的hover移出，计时器能够正确的运行", async () => {
    const { container } = render(
      <Carousel duration={1000}>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    );
    act(() => {
      jest.advanceTimersByTime(1100);
    });
    expect(container.querySelector(".list")?.getAttribute("style")).toBe(
      "transform: translateX(-100%);"
    );
    //hover 1100ms
    await userEvent.hover(document.querySelector(".list") as Element);
    act(() => {
      jest.advanceTimersByTime(1100);
    });
    expect(container.querySelector(".list")?.getAttribute("style")).toBe(
      "transform: translateX(-100%);"
    );
    // unhover 500ms
    await userEvent.unhover(document.querySelector(".list") as Element);
    act(() => {
      jest.advanceTimersByTime(500);
    });
    expect(container.querySelector(".list")?.getAttribute("style")).toBe(
      "transform: translateX(-100%);"
    );
    // hover 1100ms
    await userEvent.hover(document.querySelector(".list") as Element);
    act(() => {
      jest.advanceTimersByTime(1100);
    });
    expect(container.querySelector(".list")?.getAttribute("style")).toBe(
      "transform: translateX(-100%);"
    );
    // unhover 300ms
    await userEvent.unhover(document.querySelector(".list") as Element);
    act(() => {
      jest.advanceTimersByTime(300);
    });
    expect(container.querySelector(".list")?.getAttribute("style")).toBe(
      "transform: translateX(-100%);"
    );
    act(() => {
      jest.advanceTimersByTime(200);
    });
    expect(container.querySelector(".list")?.getAttribute("style")).toBe(
      "transform: translateX(-200%);"
    );
  });

  it("子元素数量变化的时候，进度条重置", () => {
    const { rerender, container } = render(
      <Carousel duration={1000}>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    );
    act(() => {
      jest.advanceTimersByTime(1100);
    });
    expect(container.querySelector(".list")?.getAttribute("style")).toBe(
      "transform: translateX(-100%);"
    );

    rerender(
      <Carousel duration={1000}>
        <div>1</div>
        <div>2</div>
      </Carousel>
    );
    expect(container.querySelector(".list")?.getAttribute("style")).toBe(
      "transform: translateX(-0%);"
    );
  });
});
