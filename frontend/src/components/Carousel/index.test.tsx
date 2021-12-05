import React from "react";
import { render } from "@testing-library/react";
import Carousel from ".";
import pic1 from "../../assets/pic1.jpeg";
import pic2 from "../../assets/pic2.jpeg";
import pic3 from "../../assets/pic3.jpeg";

describe(" Carousel 组件测试", () => {
  const imageList = [pic1, pic2, pic3];

  test("组件图片正常渲染", () => {
    const { container } = render(<Carousel itemList={imageList} />);
    const firstPicture = container.querySelectorAll(`img`);
    expect(firstPicture.length).toBe(3);
    const indecator = container.querySelector(".indecator-wrapper");
    expect(indecator).toBeInTheDocument();
  });

  test("组件 indicator 正常渲染", () => {
    const { container } = render(<Carousel itemList={imageList} />);
    const indecator = container.querySelector(".indecator-wrapper");
    expect(indecator).toBeInTheDocument();
  });

  test("indicator 隐藏的场景", () => {
    const { container } = render(
      <Carousel itemList={imageList} showIndicator={false} />
    );
    const indecator = container.querySelector(".indecator-wrapper");
    expect(indecator).toBe(null);
  });
});
