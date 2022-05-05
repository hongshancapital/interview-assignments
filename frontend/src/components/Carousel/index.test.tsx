import React from "react";
import { render, screen } from "@testing-library/react";
import Carousel from "./index";

test("render carousel with zero child", () => {
  const { getByTestId } = render(<Carousel items={[]} />);
  const carousel = getByTestId("carousel");
  expect(carousel).toBeInTheDocument();

  const carouselItem = screen.queryAllByTestId("carousel-item");
  expect(carouselItem).toHaveLength(0);

  const carouselProgressBarItem = screen.queryAllByTestId(
    "carousel-progress-bar-item"
  );
  expect(carouselProgressBarItem).toHaveLength(0);
});

test("render carousel with children", async () => {
  const { getByTestId, getAllByTestId, getByText } = render(
    <Carousel items={[{ titles: ["test1"] }, { texts: ["test2"] }]}></Carousel>
  );

  const item1 = getByText("test1");
  expect(item1).toBeInTheDocument();

  const item2 = getByText("test2");
  expect(item2).toBeInTheDocument();

  const carousel = getByTestId("carousel");
  expect(carousel).toBeInTheDocument();

  const carouselItem = getAllByTestId("carousel-item");
  expect(carouselItem).toHaveLength(2);

  const carouselProgressBarItem = getAllByTestId("carousel-progress-bar-item");
  expect(carouselProgressBarItem).toHaveLength(2);
});

test("color of item should work", async () => {
  const { getByTestId } = render(
    <Carousel items={[{ color: "#fff" }]}></Carousel>
  );

  const item = getByTestId("carousel-inner-item");

  // See [toHaveStyle Color not working as expected](https://github.com/testing-library/jest-dom/issues/350)
  expect(item).toHaveStyle({ color: "rgb(255, 255, 255)" });
});

test("backgroundImageURL of item should work", async () => {
  const { getByTestId } = render(
    <Carousel items={[{ backgroundImageURL: "/test.jpg" }]}></Carousel>
  );

  const item = getByTestId("carousel-inner-item");

  expect(item).toHaveStyle({ backgroundImage: `url(${"/test.jpg"})` });
});
