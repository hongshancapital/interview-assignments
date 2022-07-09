import React from "react";
import { render } from "@testing-library/react";
import Carousel from "./index";

test("test Carousel coomponent", () => {
  const { container } = render(<Carousel />);
  const progressBar = container.querySelector(".progressbar");
  const carousel = container.querySelector(".carousel");
  expect(progressBar).toBeNull();
  expect(carousel).toBeNull();
});
