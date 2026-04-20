import React from "react";
import { render } from "@testing-library/react";
import App from "./App";

test("renders learn react link", () => {
  render(<App />);
  const container = document.querySelector(".App");
  expect(container).toHaveStyle(`width: 100vw; height: 100vh`);
});
