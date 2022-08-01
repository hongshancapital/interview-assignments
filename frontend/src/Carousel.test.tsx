import React from "react";
import { act, cleanup, fireEvent, render } from "@testing-library/react";
import { CarouselDemo, carouselDemoProps, list } from "./CarouselDemo";
import random from "lodash.random";

const sleep = (time: number = 300) =>
  new Promise((resolve) => setTimeout(resolve, time));

afterEach(() => {
  cleanup();
});

describe("test Carousel", () => {
  // 随机选择一个dot点击检查
  test("check random click dots", async () => {
    const { container } = render(<CarouselDemo />);
    const buttons = container.querySelectorAll(
      ".my-carousel-dots .my-carousel-dot"
    );
    const randomIndex = random(
      0,
      buttons.length - 1 > 0 ? buttons.length - 1 : 0
    );
    const button = buttons[randomIndex];
    act(() => {
      fireEvent.click(button);
    });
    const activeDot = container.querySelector(
      '.my-carousel-dots .my-carousel-dot[data-active="true"]'
    );
    const activeIndex = activeDot?.getAttribute("data-index");
    expect(activeIndex).toBe(String(randomIndex));
    // await cleanup();
  });
  // 关闭自动轮播
  test("check turn off autoplay", async () => {
    const component = render(<CarouselDemo autoplay={false} />);
    expect(component).toMatchSnapshot();
    await sleep(carouselDemoProps.delay + 300);
    expect(component).toMatchSnapshot();
    // await cleanup();
  });
  // 开启轮播
  test("check turn on autoplay", async () => {
    const { container } = render(<CarouselDemo autoplay={true} />);
    await act(async () => {
      await sleep(carouselDemoProps.delay + 300);
      const activeDot = container.querySelector(
        '.my-carousel-dots .my-carousel-dot[data-active="true"]'
      );
      const activeIndex = activeDot?.getAttribute("data-index");
      expect(activeIndex).toBe("1");
    });
    // await cleanup();
  });
  test("check text render", async () => {
    const { getByText } = render(<CarouselDemo />);
    list.forEach((data) => {
      const titleEl = getByText(data.title);
      expect(titleEl).toBeInTheDocument();
    });
    // await cleanup();
  });
});
