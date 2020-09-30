import React from "react";
import { render } from "@testing-library/react";

import CarouselItem from "./CarouselItem";

test("should render children", async () => {
  const doc = render(<CarouselItem><h1>Hello World!</h1></CarouselItem>);

  expect(doc.getByRole("heading", {
    level: 1,
  })).toHaveTextContent("Hello World");
});

test("show classes", () => {
  const doc = render(
    <CarouselItem isActive={true} isPrevious={true} isNext={true} direction="ltr">
    </CarouselItem>
  );

  expect(doc.container.firstElementChild).toBeInstanceOf(HTMLDivElement);
  expect(doc.container.firstElementChild).toHaveClass("root active previous next ltr");
});

test("show style", () => {
  const doc = render(
    <CarouselItem background="test.png" duration={100} color="black">
    </CarouselItem>
  );

  expect(doc.container.firstElementChild).toBeInstanceOf(HTMLDivElement);
  expect(doc.container.firstElementChild).toHaveStyle({
    backgroundImage: 'url(test.png)',
    transitionDuration: '100ms',
    color: 'black',
  });
});
