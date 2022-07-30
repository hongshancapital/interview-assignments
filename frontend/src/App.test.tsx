import React from "react";
import { render } from "@testing-library/react";
import App from "./App";

test("目标组件正常加载", () => {
  const { getByTestId } = render(<App />);
  const carouselElement = getByTestId("carousel");
  expect(carouselElement).toBeInTheDocument();
});
