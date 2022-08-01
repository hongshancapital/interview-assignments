import React from "react";
import { render } from "@testing-library/react";
import CarouseItem from "./CarouselItem";

describe("轮播图子组件", () => {
  it("CarouselItem test", () => {
    const warpper = render(<CarouseItem>1</CarouseItem>);
    expect(warpper).toMatchSnapshot();
    expect(
      warpper.container.querySelector(".carousel-item")
    ).toBeInTheDocument();
  });
});
