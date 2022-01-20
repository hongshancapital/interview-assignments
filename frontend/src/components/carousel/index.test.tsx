import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import Carousel from "./index";

const text: string[] = ["test1", "test2", "test3", "test4"];
const slides = text.map((item: String, index: number) => (
  <div key={index} data-testid={`testid-${index}`}>
    <h1>{item}</h1>
  </div>
));

const testIndex = 0;
const childrenLen = text.length;

test("carousel basic render", () => {
  const customClassName = 'custom-carousel'
  render(<Carousel className={customClassName}>{slides}</Carousel>);
  expect(screen.getByText(text[testIndex])).toBeInTheDocument();

  const slideElement = screen.getByTestId(`testid-${testIndex}`);
  const carouselWrapper = slideElement.parentNode;
  // 节点渲染成功
  expect(carouselWrapper.className).toBe("carousel-slide");
  const carouseContainer = carouselWrapper.parentNode;
  expect(carouseContainer.className).toBe("carousel-slider");
  expect(carouseContainer.children.length).toBe(childrenLen);

  // 自定义 className
  const topElement = carouseContainer.parentNode;
  expect(topElement.className).toBe(`carousel ${customClassName}`);

  const dotElement = carouseContainer.nextSibling;
  // 底部按钮
  expect(dotElement.className).toBe("carousel-dots");
  // 底部按钮数量
  expect(dotElement.children.length).toBe(childrenLen);
});

test("carousel event", () => {
  render(<Carousel autoPlay={false}>{slides}</Carousel>);
  const slideElement = screen.getByTestId(`testid-${testIndex}`);
  const carouseContainer = slideElement.parentNode.parentNode;
  const dotElement = slideElement.parentNode.parentNode.nextSibling;
  // 点击第几个，从 1 开始算
  const clickIndex = 1;
  fireEvent.click(dotElement.children[clickIndex - 1]);
  const { transform } = carouseContainer.style;
  expect(transform).toBe(
    `translateX(${(-(clickIndex - 1) / childrenLen) * 100}%)`
  );
});
