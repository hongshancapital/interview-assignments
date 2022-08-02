import { render } from "@testing-library/react";
import React from "react";
import Carousel from "..";
import TEST_SLIDES from "./data/slides.json";

test("render", () => {
  const { baseElement } = render(<Carousel items={TEST_SLIDES} />);
  expect(baseElement).toMatchSnapshot();
});
