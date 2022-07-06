import React from "react";
import { render, act } from "@testing-library/react";
import App from "./App";

test("renders app", () => {
  act(() => {
    render(<App />);
  });
  const slidesEles = document.querySelectorAll(".carousel-slides__item");
  expect(slidesEles.length).toBe(3);
});
