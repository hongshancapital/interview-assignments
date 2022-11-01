import React from "react";
import { render } from "@testing-library/react";
import { act } from "react-dom/test-utils";
import App from "./App";

describe("Carousel", () => {
  it("check the container", () => {
    const { container } = render(<App />);
    const appElem = container.querySelector(".App");
    const carouselElem = container.querySelector(".carousel");
    expect(carouselElem).not.toBeNull();
    expect(appElem).toContainHTML(carouselElem!.innerHTML);
  });
  it("check carousel render", () => {
    jest.useFakeTimers();
    const { container } = render(<App />);
    const box = container.querySelector(".carousel-content");
    expect(box).toHaveStyle(` transform: translateX(${-0 * 100}%)`);

    act(() => {
      jest.advanceTimersByTime(3000);
    });

    expect(box).toHaveStyle(` transform: translateX(${-1 * 100}%)`);

    act(() => {
      jest.advanceTimersByTime(3000);
    });

    expect(box).toHaveStyle(` transform: translateX(${-2 * 100}%)`);
  });
});
