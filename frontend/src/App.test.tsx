import React from "react";
import { render } from "@testing-library/react";
import App from "./App";
import scssStyle from "@/components/Carousel/index.module.scss";

test("挂载Carousel ", () => {
  const { container } = render(<App />);
  expect(container.querySelector(`.${scssStyle.container}`)).toBeTruthy();
});
