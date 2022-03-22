import React from "react";
import { act, fireEvent, render } from "@testing-library/react";
import { carouselData } from "../../../_mockData/carousel";
import Carousel from "../index";

describe("test Carousel component", () => {
  let setSize: jest.Mock;
  let setCurrentIndex: jest.Mock;

  beforeEach(() => {
    setSize = jest.fn();
    setCurrentIndex = jest.fn();

    jest
      .spyOn(React, "useState")
      .mockImplementationOnce(() => ["", setSize])
      .mockImplementationOnce(() => ["", setCurrentIndex]);

    jest.useFakeTimers();
  });

  test("should render correctly", () => {
    const { getByText } = render(<Carousel carouselData={carouselData} />);
    expect(getByText(carouselData[0].title[0])).toBeInTheDocument();
  });

  test("should call setSize and setCurrentIndex when component mount", () => {
    const size = {
      width: 100,
      height: 100,
    };

    Object.defineProperties(window.HTMLElement.prototype, {
      clientWidth: {
        get: function () {
          return size.width;
        },
      },
      clientHeight: {
        get: function () {
          return size.height;
        },
      },
    });

    render(<Carousel carouselData={carouselData} />);
    expect(setSize).toHaveBeenCalledWith(size);
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
