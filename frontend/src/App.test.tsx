//@ts-nocheck
import React from 'react';
import { render } from '@testing-library/react';
import Carousel from './Carousel';

const items = [
  {
    id: 1,
    image:iphone,
    title: "iPhone",
    description:
      "The sun rises over the horizon, painting the sky with warm colors.",
    prices:'Starting at $399.'
  },
  {
    id: 2,
    image:tablet,
    title: "Tablet",
    description:
      "KKKKKK ises over the horizon, painting the sky with warm colors.",
    prices:'Starting at $499.'
  },
  {
    id: 3,
    image:airpods,
    title: "airpods",
    description:
      "KKKKKK ises over the horizon, painting the sky with warm colors.",
    prices:'Starting at $599.'
  }
]
// 定义一个测试用例，用来测试Carousel组件能否正确切换到下一个项目
test("should switch to next item when clicking next button", () => {
  // 使用render函数渲染Carousel组件，并传入items数组作为props
  render(<Carousel items={items} />);

  // 获取当前显示的项目的标题和描述元素
  const titleElement = screen.getByText("Sunrise");
  const descriptionElement = screen.getByText(
    "The sun rises over the horizon, painting the sky with warm colors."
  );

  // 断言当前显示的项目是第一个项目
  expect(titleElement).toBeInTheDocument();
  expect(descriptionElement).toBeInTheDocument();

  // 获取下一个按钮元素
  const nextButton = screen.getByRole("button", { name: /next/i });

  // 模拟点击下一个按钮的事件
  fireEvent.click(nextButton);

  // 获取新显示的项目的标题和描述元素
  const newTitleElement = screen.getByText("Landscape");
  const newDescriptionElement = screen.getByText(
    "A beautiful view of the mountains and the lake."
  );

  // 断言新显示的项目是第二个项目
  expect(newTitleElement).toBeInTheDocument();
  expect(newDescriptionElement).toBeInTheDocument();
});