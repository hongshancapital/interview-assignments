/**
 * @jest-environment jsdom
 */
import React from "react";
import { render, fireEvent } from "@testing-library/react";
import App from "./App";
import { act } from "react-dom/test-utils";

beforeEach(() => {
  jest.useFakeTimers();
});

test("carousel auto play", async () => {
  const { container } = render(<App />);
  const carouselContainer = container.querySelector(".carousel-container");

  expect(carouselContainer).toHaveStyle("transform: translateX(-0%);");
  act(() => {
    jest.runOnlyPendingTimers();
  });
  expect(carouselContainer).toHaveStyle(
    `transform: translateX(-${(1 / 3) * 100}%);`
  );
  act(() => {
    jest.runOnlyPendingTimers();
  });
  expect(carouselContainer).toHaveStyle(
    `transform: translateX(-${(2 / 3) * 100}%);`
  );
});

test("click carousel bar", async () => {
  const { container } = render(<App />);
  const carouselBars = container.querySelectorAll(
    ".carousel-bar-item-container"
  );
  const carouselContainer = container.querySelector(".carousel-container");
  // click index: 1 carousel bar
  await fireEvent.click(carouselBars[1]);
  expect(carouselContainer).toHaveStyle(
    `transform: translateX(-${(1 / 3) * 100}%);`
  );

  // click index: 0 carousel bar
  await fireEvent.click(carouselBars[0]);
  expect(carouselContainer).toHaveStyle(`transform: translateX(-0%);`);

  // click index: 2 carousel bar
  await fireEvent.click(carouselBars[2]);
  expect(carouselContainer).toHaveStyle(
    `transform: translateX(-${(2 / 3) * 100}%);`
  );
});

test("mouseEnter and mouseLeave carousel", async () => {
  const { container } = render(<App />);

  const carouselContainer = container.querySelector(".carousel-container");
  expect(carouselContainer).toHaveStyle("transform: translateX(-0%);");
  // first mock mouseEnter
  await fireEvent.mouseOver(carouselContainer!);
  jest.advanceTimersByTime(3000);
  expect(carouselContainer).toHaveStyle(`transform: translateX(-0%);`);
  // // fist mock mouseLeave
  await fireEvent.mouseOut(carouselContainer!);
  jest.advanceTimersByTime(2800);
  expect(carouselContainer).toHaveStyle(`transform: translateX(-0%);`);
  jest.advanceTimersByTime(200);
  expect(carouselContainer).toHaveStyle(
    `transform: translateX(-${(1 / 3) * 100}%);`
  );

  // second mock mouseEnter
  await fireEvent.mouseOver(carouselContainer!);
  jest.advanceTimersByTime(3000);
  expect(carouselContainer).toHaveStyle(`transform: translateX(-${(1 / 3) * 100}%);`);
  // // second mock mouseLeave
  await fireEvent.mouseOut(carouselContainer!);
  jest.advanceTimersByTime(2800);
  expect(carouselContainer).toHaveStyle(`transform: translateX(-${(1 / 3) * 100}%);`);
  jest.advanceTimersByTime(200);
  expect(carouselContainer).toHaveStyle(
    `transform: translateX(-${(2 / 3) * 100}%);`
  );

  // third mock mouseEnter
  await fireEvent.mouseOver(carouselContainer!);
  jest.advanceTimersByTime(3000);
  expect(carouselContainer).toHaveStyle(`transform: translateX(-${(2 / 3) * 100}%);`);
  // // third mock mouseLeave
  await fireEvent.mouseOut(carouselContainer!);
  jest.advanceTimersByTime(2800);
  expect(carouselContainer).toHaveStyle(`transform: translateX(-${(2 / 3) * 100}%);`);
  jest.advanceTimersByTime(200);
  expect(carouselContainer).toHaveStyle(
    `transform: translateX(-0%);`
  );
});
