import React from "react";
import { render, fireEvent, act } from "@testing-library/react";
import "jest-styled-components";
import Carousel from "../index";
import { loopFrame } from "../style";

describe("Carousel", () => {
  const slideItems = [
    <div>slide 1</div>,
    <div>slide 2</div>,
    <div>slide 3</div>,
  ];

  const target = render(<Carousel slideItems={slideItems} />);

  test("Carousel render", () => {
    // 验证渲染是否正常
    const { getByText } = target;
    const slide1 = getByText(/slide 1/i);
    const slide2 = getByText(/slide 2/i);
    const slide3 = getByText(/slide 3/i);
    expect(slide1).toBeInTheDocument();
    expect(slide2).toBeInTheDocument();
    expect(slide3).toBeInTheDocument();
  });


  test("Carousel setInterval", () => {
    jest.useFakeTimers();
    jest.spyOn(global, 'setInterval');
    const slideItems = [
      <div>slide 1</div>,
    ];
  
    render(<Carousel slideItems={slideItems} />);
  
    act(() => {
      jest.runOnlyPendingTimers();
    });

    expect(setInterval).toHaveBeenCalled();
  });

  test("Carousel indicator click", () => {
    const { container } = render(<Carousel slideItems={slideItems} />);
    const loopContainer = container.getElementsByClassName("loop-container")[0];

    for (let i = 0; i < 3; i++) {
      // 验证点击事件
      fireEvent.click(container.getElementsByClassName("indicator")[i]);

      expect(loopContainer).toHaveStyleRule(
        "animation-name",
        loopFrame({ total: 3, current: i }).getName()
      );
    }
  });

  test("Carousel unmount", () => {
    const { container, unmount } = target
    unmount?.();
    expect(container.innerHTML).toBe('');
  });

});
