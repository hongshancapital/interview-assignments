import React from "react";
import { fireEvent, render, screen } from "@testing-library/react";
import App from "./App";
import { Carousel, type CarouselProps } from "./components";

// test('renders learn react link', () => {
//   const { getByText } = render(<App />);
//   const linkElement = getByText(/learn react/i);
//   expect(linkElement).toBeInTheDocument();
// });

const defaultProps: CarouselProps = {
  showIndicator: true,
  autoplay: true,
  currentIndex: 0,
  duration: 3000,
  easing: "linear",
  speed: 500,
  onChange: jest.fn(),
};

describe("test carousel component", () => {
  it("should display the carousel component", () => {
    const wrapper = render(<App />);
    expect(wrapper.container.querySelector(".carousel")).toBeInTheDocument();
    screen.debug();
  });

  it("should not display the indicator component", () => {
    const { queryByTestId } = render(
      <Carousel {...defaultProps} showIndicator={false}>
        <div className="carousel_item" />
      </Carousel>
    );
    const indicator = queryByTestId("carousel_indicator");
    expect(indicator).not.toBeInTheDocument();
  });

  it("should trigger the onChange callback after click on corresponding indicator element", () => {
    render(
      <Carousel {...defaultProps}>
        <div className="carousel_item">hello</div>
        <div className="carousel_item">world</div>
      </Carousel>
    );
    const carouselIndicatorWrapper = screen.getByTestId("carousel_indicator");
    const indicatorElements = carouselIndicatorWrapper.querySelectorAll(
      ".carousel_indicator_block"
    );
    expect(indicatorElements.length).toBe(2);
    fireEvent.click(indicatorElements[0]);
    expect(defaultProps.onChange).toBeCalledWith(0);
    fireEvent.click(indicatorElements[1]);
    expect(defaultProps.onChange).toBeCalledWith(1);
  });
});
