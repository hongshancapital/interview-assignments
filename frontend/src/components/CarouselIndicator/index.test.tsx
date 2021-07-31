import React from "react";
import {render} from "@testing-library/react";
import CarouselIndicator from "./index";

test("renders carousel indicator", () => {
  const SWITCH_INTERVAL = 3000;
  render(
    <CarouselIndicator actived={false} switchInterval={SWITCH_INTERVAL} />
  );
  // the carousel indicator should be rendered
  const indicatorElements = document.getElementsByClassName("carousel-indicator");
  expect(indicatorElements.length).toBe(1);
  // the carousel indicator should have style "transitionDuration"
  const progressElements = document.getElementsByClassName("carousel-indicator-progress");
  expect(progressElements.length).toBe(1);
  expect((progressElements[0] as HTMLElement).style.transitionDuration).toBe(`${SWITCH_INTERVAL}ms`);
});

test("renders actived carousel indicator", () => {
  render(
    <CarouselIndicator actived={true} />
  );
  // the carousel indicator should be rendered
  const indicatorElements = document.getElementsByClassName("carousel-indicator-actived");
  expect(indicatorElements.length).toBe(1);
});

test("renders carousel indicator with custom class name", () => {
  const CLS_NAME = "test-cls";
  render(
    <CarouselIndicator actived={false} className={CLS_NAME} />
  );
  const carouselElement = document.getElementsByClassName("carousel-indicator")[0];
  expect(carouselElement.classList).toContain(CLS_NAME);
});
