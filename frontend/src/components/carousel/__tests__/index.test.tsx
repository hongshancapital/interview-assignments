import React from "react";
import { fireEvent, render } from "@testing-library/react";
import { carouselData } from "../../../_mockData/carousel";
import Carousel from "../index";

describe("test Carousel component", () => {
  let setWidth: jest.Mock;
  let setCurrentIndex: jest.Mock;

  beforeEach(() => {
    setWidth = jest.fn();
    setCurrentIndex = jest.fn();

    jest
      .spyOn(React, "useState")
      .mockImplementationOnce(() => ["", setWidth])
      .mockImplementationOnce(() => ["", setCurrentIndex]);

    jest.useFakeTimers();
  });

  test("should render correctly", () => {
    const { getByText } = render(<Carousel carouselData={carouselData} />);
    expect(getByText(carouselData[0].title[0])).toBeInTheDocument();
  });

  test("should call setSize and setCurrentIndex when component mount", () => {
    const width = 100;

    Object.defineProperties(window.HTMLElement.prototype, {
      clientWidth: {
        get: function () {
          return width;
        },
      },
    });

    render(<Carousel carouselData={carouselData} />);
    expect(setWidth).toHaveBeenCalledWith(width);
    jest.advanceTimersByTime(3000);
    expect(setCurrentIndex).toBeCalledTimes(1);
  });

  test("should call setCurrentIndex when click li element", () => {
    const { container } = render(<Carousel carouselData={carouselData} />);
    const liElement = container.querySelectorAll("li");
    fireEvent.click(liElement[2]);
    expect(setCurrentIndex).toHaveBeenCalledWith(2);
    expect(liElement.length === carouselData.length);
  });
});
