import React from "react";
import { render } from "@testing-library/react";
import App from "./App";

test("renders learn react link", () => {
  const { container } = render(<App />);
  const app = container.querySelector(".App");
  const carousel = container.querySelector(".carousel");
  expect(carousel).not.toBeNull();
  expect(app).toContainHTML(carousel!.innerHTML);
});
