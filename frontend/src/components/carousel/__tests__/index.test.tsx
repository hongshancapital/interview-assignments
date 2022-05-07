import React from "react";
import { render, fireEvent } from "@testing-library/react";
import { act } from "react-dom/test-utils";

import Carousel from "../index";

describe("carousel", () => {
  beforeEach(() => {
    global.ResizeObserver = jest.fn().mockImplementation(() => ({
      observe: jest.fn(),
      unobserve: jest.fn(),
      disconnect: jest.fn(),
    }));
    jest.useFakeTimers();
  });

  afterEach(() => {
    jest.useRealTimers();
  });

  test("render the correct number of children and indicator", () => {
    const { container } = render(
      <Carousel>
        <div>1</div>
        <div>2</div>
      </Carousel>
    );
    expect(container.querySelectorAll(".carouselItem").length).toBe(3);
    expect(container.querySelectorAll(".progress").length).toBe(2);
  });

  test("test indicator click", () => {
    const fn = jest.fn();
    const { container } = render(
      <Carousel onChange={fn}>
        <div>1</div>
        <div>2</div>
      </Carousel>
    );
    fireEvent.click(container.querySelectorAll(".progress")[1]);
    expect(fn).toHaveBeenCalledWith(1);
  });

  test("test autoplay", () => {
    const fn = jest.fn();
    render(
      <Carousel onChange={fn} autoplay>
        <div>1</div>
        <div>2</div>
      </Carousel>
    );
    act(() => {
      jest.runAllTimers();
    });
    expect(fn).toHaveBeenCalledWith(1);
  });
});
