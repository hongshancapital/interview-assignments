import React from "react";
import { render } from "@testing-library/react";
import TargetCarousel from '../index';


describe("TargetCarousel", () => {

  const target = render(<TargetCarousel />);

  test("Carousel render", () => {
    // 验证渲染是否正常
    const { getByText } = target;
    const slide1 = getByText(/Lots to love. Less to spend/i);
    const slide2 = getByText(/Just the right amount of everything./i);
    const slide3 = getByText(/Buy a tablet or xPhone for college./i);
    expect(slide1).toBeInTheDocument();
    expect(slide2).toBeInTheDocument();
    expect(slide3).toBeInTheDocument();
  })
});
