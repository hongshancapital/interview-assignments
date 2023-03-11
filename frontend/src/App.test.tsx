import React from "react";
import { render } from "@testing-library/react";
import App, {
  Carousel,
  CarouselItem,
  CarouselPagination,
  CarouselPaginationItem,
} from "./App";

test("App should render normally", () => {
  const { container } = render(<App />);

  expect(container.querySelector(".carousel")).toBeInTheDocument();
});
test("Carousel Item should render normally", () => {
  const { getByText } = render(<CarouselItem>content</CarouselItem>);

  expect(getByText("content")).toBeInTheDocument();
});

test("CarouselPaginationItem should render normally", () => {
  const { container } = render(
    <CarouselPaginationItem show={true} onFinished={jest.fn()} />
  );

  expect(container.querySelector(".running")).toBeInTheDocument();
});

test("CarouselPagination should render normally", () => {
  const { container } = render(
    <CarouselPagination total={5} currentIndex={0} onFinished={jest.fn()} />
  );
  const allItems = container.querySelectorAll(
    ".carouselPaginationItemAnimation"
  );

  expect(allItems.length).toBe(5);
  expect(allItems[0]).toHaveClass("running");
});

test("Carousel should render normally", () => {
  const data = [1, 2, 3, 4];

  const { container } = render(
    <Carousel>
      {data.map((item) => {
        return <CarouselItem key={item}>{item}</CarouselItem>;
      })}
    </Carousel>
  );

  expect(container.querySelector(".carousel")).toBeInTheDocument();
  expect(container.querySelector(".carouselItemWrapper")).toBeInTheDocument();
  expect(container.querySelector(".carouselPagination")).toBeInTheDocument();
});
