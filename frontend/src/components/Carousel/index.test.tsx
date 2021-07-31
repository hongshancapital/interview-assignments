import React from "react";
import {render, act, fireEvent} from "@testing-library/react";
import Carousel from "./index";

beforeEach(() => {
  jest.useFakeTimers();
});

afterEach(() => {
  jest.useRealTimers();
});

test("renders carousel with multiple slices", () => {
  const {queryAllByText} = render(
    <Carousel>
      <div>slice1</div>
      <div>slice2</div>
    </Carousel>
  );
  // the carousel slice should be rendered
  const sliceElements = queryAllByText(/^slice\d$/);
  expect(sliceElements.length).toBe(2);
  // the carousel indicator should be rendered
  const indicatorElements = document.getElementsByClassName("carousel-indicator");
  expect(indicatorElements.length).toBe(2);
  // the carousel switching should be enabled
  expect(indicatorElements[0].classList).toContain("carousel-indicator-actived");
});

test("the carousel should switch periodically and circularly", () => {
  const SWITCH_INTERVAL = 4000;
  render(
    <Carousel switchInterval={SWITCH_INTERVAL}>
      <div>slice1</div>
      <div>slice2</div>
    </Carousel>
  );
  const sliceContainerElement = document.getElementsByClassName("carousel-slices")[0];
  expect(sliceContainerElement).toBeInTheDocument();
  expect((sliceContainerElement as HTMLElement).style.left).toBe("0%");
  // the carousel should switch to the 2nd slice after 1 switch interval
  act(() => {
    jest.advanceTimersByTime(SWITCH_INTERVAL);
  });
  expect((sliceContainerElement as HTMLElement).style.left).toBe("-100%");
  const indicatorElements = document.getElementsByClassName("carousel-indicator");
  expect(indicatorElements[1].classList).toContain("carousel-indicator-actived");
  // the carousel should switch back to the 1st slice after 2 switch interval
  act(() => {
    jest.advanceTimersByTime(SWITCH_INTERVAL);
  });
  expect((sliceContainerElement as HTMLElement).style.left).toBe("0%");
  expect(indicatorElements[0].classList).toContain("carousel-indicator-actived");
});

test("the carousel should pause and resume switching when mouse hovered and leaved", () => {
  const SWITCH_INTERVAL = 3000;
  render(
    <Carousel pauseOnHover={true} switchInterval={SWITCH_INTERVAL}>
      <div>slice1</div>
      <div>slice2</div>
    </Carousel>
  );
  act(() => {
    jest.advanceTimersByTime(SWITCH_INTERVAL);
  });
  // should pause switching when mouse hovered on the slice
  const sliceContainerElement = document.getElementsByClassName("carousel-slices")[0];
  fireEvent.mouseEnter(sliceContainerElement);
  act(() => {
    jest.advanceTimersByTime(SWITCH_INTERVAL);
  });
  expect((sliceContainerElement as HTMLElement).style.left).toBe("-100%");
  const indicatorElements = document.getElementsByClassName("carousel-indicator-actived");
  expect(indicatorElements.length).toBe(0);
  // should resume switching when mouse leaved from the slice
  fireEvent.mouseLeave(sliceContainerElement);
  act(() => {
    jest.advanceTimersByTime(SWITCH_INTERVAL + 1000);
  });
  expect((sliceContainerElement as HTMLElement).style.left).toBe("0%");
  expect(indicatorElements.length).toBe(1);
});

test("renders carousel with only 1 slice", () => {
  const {getAllByText} = render(
    <Carousel>
      <div>slice</div>
    </Carousel>
  );
  // the carousel slice should be rendered
  const sliceElements = getAllByText("slice");
  expect(sliceElements.length).toBe(1);
  // the carousel indicator should be rendered
  const indicatorElements = document.getElementsByClassName("carousel-indicator");
  expect(indicatorElements.length).toBe(1);
  // the carousel switching should be disabled
  const activedIndicatorElements = document.getElementsByClassName("carousel-indicator-actived");
  expect(activedIndicatorElements.length).toBe(0);
});

test("renders carousel without slice", () => {
  render(<Carousel />);
  const sliceElements = document.getElementsByClassName("carousel-slice");
  expect(sliceElements.length).toBe(0);
});

test("renders carousel slices lazily", () => {
  const SWITCH_INTERVAL = 3000;
  const {queryByText} = render(
    <Carousel lazyRenderEnabled={true} switchInterval={SWITCH_INTERVAL}>
      <div>slice1</div>
      <div>slice2</div>
      <div>slice3</div>
      <div>slice4</div>
    </Carousel>
  );
  // the 3rd slice should not rendered because it is not adjacent with the 1st slice
  expect(queryByText("slice3")).not.toBeInTheDocument();
  // the 3rd slice should rendered after switching to the 2nd slice
  act(() => {
    jest.advanceTimersByTime(SWITCH_INTERVAL);
  });
  expect(queryByText("slice3")).toBeInTheDocument();
  // the lazy rendered slices should always exist after rendered
  // switch to the 1st slice
  act(() => {
    jest.advanceTimersByTime(SWITCH_INTERVAL * 3);
  });
  expect(queryByText("slice3")).toBeInTheDocument();
});

test("renders carousel with custom class name", () => {
  const CLS_NAME = "test-cls";
  render(
    <Carousel className={CLS_NAME}>
      <div>slice</div>
    </Carousel>
  );
  const carouselElement = document.getElementsByClassName("carousel-container")[0];
  expect(carouselElement.classList).toContain(CLS_NAME);
});
