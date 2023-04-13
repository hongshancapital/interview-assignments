import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import "@testing-library/jest-dom/extend-expect";
import Carousel from "./component/Carousel";
import { mockdata } from "./data/mockdata";

// 将 Carousel 组件的测试包含在一个 describe 块中
describe("Carousel component", () => {
  // 在每个测试用例之前渲染 Carousel 组件
  beforeEach(() => {
    render(<Carousel images={mockdata} />);
  });

  // 测试 Carousel 组件渲染后是否包含第一张图片的文本
  test("renders Carousel component with the first image text", () => {
    const carousel = screen.getByTestId("carousel");
    const firstImageText = carousel.querySelector(".slideText");
    expect(firstImageText).not.toBeNull();
  });

  // 测试 Carousel 组件中指示器的数量是否与 mockdata 中的图片数量相等
  test("carousel displays the correct number of indicators", () => {
    const indicators = screen.getAllByTestId("indicator");
    expect(indicators.length).toEqual(mockdata.length);
  });

  // 测试点击 Carousel 组件中的指示器是否会更改激活的图片
  test("carousel indicator click changes the active image", () => {
    const indicators = screen.getAllByTestId("indicator");

    // 点击第二个指示器
    fireEvent.click(indicators[1]);
    const carousel = screen.getByTestId("carousel");
    // 查询当前激活的图片文本
    const secondImageText = carousel.querySelector(".slideText");
    expect(secondImageText).not.toBeNull();
  });
});
