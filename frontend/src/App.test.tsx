import React from "react";
import { render } from "@testing-library/react";
import App from "./App";
import dataSlide from "./data-slides";

describe("验证元素已全部渲染", () => {
  const {unmount} = render(<App />);
  const slideElements = document.querySelectorAll(".slide");

  expect(slideElements.length).toBe(dataSlide.length);

  dataSlide.forEach((item, index) => {
    test(`第${index}个幻灯片，包含title`, () => {
      expect(slideElements[index]).toContainHTML(item.title);
    });

    test(`第${index}个幻灯片，包含描述`, () => {
      if (item.desc) {
        expect(slideElements[index]).toContainHTML(item.desc);
      }
    });

    test(`第${index}个幻灯片，包含背景图片`, () => {
      expect(slideElements[index]).toHaveStyle({
        backgroundImage: `url(${item.img})`,
      });
    });

    unmount();
  });

});
