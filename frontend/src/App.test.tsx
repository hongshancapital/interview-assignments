import React from "react";
import { render } from "@testing-library/react";
import App from "./App";

describe("测试App组件", () => {
  test("测试渲染是否正常r", () => {
    const { container } = render(<App />);
    const Apps = container.querySelectorAll(".App");
    expect(Apps.length).toBe(1);

    // // 检查容器组件是否渲染正常
    // const Carousel = container.querySelectorAll("[class^=container]");
    // expect(Carousel.length).toBe(1);
    // // 检查slide组件是否渲染正常
    // const slides = container.querySelectorAll("[class^=slide]");
    // expect(slides.length).toBe(3);
    // // 检查button组件是否渲染正常
    // const buttons = container.querySelectorAll("[class=indicatorContainer]");
    // expect(buttons.length).toBe(1);
  });
});
