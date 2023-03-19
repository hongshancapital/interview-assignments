import xPhoneIcon from "../../assets/iphone-icon.png";
import { render } from "@testing-library/react";
import { Slide } from "../component/slide";

describe("测试slide组件", () => {
  test("测试文字和颜色能否正常渲染", () => {
    const mockData = {
      title: ["xPhone"],
      backgroundColor: "#111111",
      description: ["Lots to love. Less to spend.", "Starting at $399."],
      textColor: "#fafafa",
      icon: xPhoneIcon,
    };
    const { container, getByText } = render(<Slide {...mockData} />);

    const slideContainer = container.firstChild;
    expect(slideContainer).toHaveStyle(
      `background-color: ${mockData.backgroundColor}`
    );

    expect(getByText(/xPhone/i).parentElement).toHaveStyle(
      `color: ${mockData.textColor}`
    );

    expect(getByText(/Lots to love. Less to spend/i).parentElement).toHaveStyle(
      `color: ${mockData.textColor}`
    );
    const image = container.querySelector("img");

    expect(image).toHaveAttribute("src");
  });

  test("测试部分数据不存在时，渲染是否正常", () => {
    const mockData = {
      title: ["xPhone"],
      backgroundColor: "#111111",
      textColor: "#fafafa",
      icon: xPhoneIcon,
    };
    const { container } = render(<Slide {...mockData} />);

    const descContainer = container.querySelector(
      "[class^=carousel-slide-desc]"
    );
    expect(descContainer?.children.length).toBe(0);

    const image = container.querySelector("image");

    expect(image).toBeNull();
  });
});
