import React from "react";
import { render } from "@testing-library/react";

import Carousel from ".";
import getSlideList from "../../utils/getSlideList";

describe(" Carousel Component Test", () => {
  const slideList = getSlideList();

  test("Component images render", () => {
    const { container } = render(<Carousel slideList={slideList} />);
    const imageList = container.querySelectorAll(`.slide-wrapper`);
    expect(imageList.length).toBe(slideList.length);
  });

  test("Component indicator render", () => {
    const { container } = render(<Carousel slideList={slideList} />);
    const indicator = container.querySelector(".indicator-wrapper");
    expect(indicator).toBeInTheDocument();
  });

  test("Component hide indicator render", () => {
    const { container } = render(<Carousel slideList={slideList} showIndicator={false} />);
    const indicator = container.querySelector(".indicator-wrapper");
    expect(indicator).toBe(null);
  });
});
