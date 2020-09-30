import React from "react";
import { render } from "@testing-library/react";

import Carousel from "./Carousel";
import CarouselItem from "./CarouselItem";

test("should not renders indicators when contain a single CarouselItem", async () => {
  const doc = render(
    <Carousel>
      <CarouselItem><h1>H1</h1></CarouselItem>
    </Carousel>
  );

  const indicator = doc.container.querySelector('ol.indicators');
  expect(indicator).toBeNull();
});

test("should renders multiple indicators", async () => {
  const doc = render(
    <Carousel>
      <CarouselItem><h1>H1</h1></CarouselItem>
      <CarouselItem><h1>H1</h1></CarouselItem>
    </Carousel>
  );

  const indicator = doc.container.querySelector('ol.indicators');
  expect(indicator).toBeInstanceOf(HTMLOListElement);
  expect(indicator?.childElementCount).toBe(2);
});

test("should renders children", async () => {
  const doc = render(
    <Carousel>
      <CarouselItem><h1>C1</h1></CarouselItem>
      <CarouselItem><h1>C2</h1></CarouselItem>
    </Carousel>
  );

  const contents = doc.getAllByRole("heading", {
    level: 1,
  });

  expect(contents).toHaveLength(2);
});
