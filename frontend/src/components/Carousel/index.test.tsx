import React from "react";
import { render, screen } from "@testing-library/react";
import Carousel from "./index";

test("render carousel with zero child", () => {
  const { getByTestId } = render(<Carousel />);
  const carousel = getByTestId("carousel");
  expect(carousel).toBeInTheDocument();

  const carouselItem = screen.queryAllByTestId("carousel-item");
  expect(carouselItem).toHaveLength(0);

  const carouselProgressBarItem = screen.queryAllByTestId(
    "carousel-progress-bar-item"
  );
  expect(carouselProgressBarItem).toHaveLength(0);
});

test("render carousel with children Carousel.Item", async () => {
  const { getByTestId, getAllByTestId } = render(
    <Carousel>
      <Carousel.Item>test1</Carousel.Item>
      <Carousel.Item>test2</Carousel.Item>
      <Carousel.Item>test3</Carousel.Item>
    </Carousel>
  );

  const carousel = getByTestId("carousel");
  expect(carousel).toBeInTheDocument();

  const carouselItem = getAllByTestId("carousel-item");
  expect(carouselItem).toHaveLength(3);

  const carouselProgressBarItem = getAllByTestId("carousel-progress-bar-item");
  expect(carouselProgressBarItem).toHaveLength(3);
});

test("render carousel with arbitrary children", async () => {
  const { getByTestId, getAllByTestId } = render(
    <Carousel>
      {null}
      test1
      <div>test2</div>
      <Carousel.Item>test3</Carousel.Item>
    </Carousel>
  );

  const carousel = getByTestId("carousel");
  expect(carousel).toBeInTheDocument();

  const carouselItem = screen.queryAllByTestId("carousel-item");
  expect(carouselItem).toHaveLength(1);

  const carouselProgressBarItem = getAllByTestId("carousel-progress-bar-item");
  expect(carouselProgressBarItem).toHaveLength(4);
});
