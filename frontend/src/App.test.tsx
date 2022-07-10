import React from "react";
import { render, act } from "@testing-library/react";
import App from "./App";

test("render app", () => {
  act(() => {
    render(<App />);
  });
  const slideElements = document.querySelectorAll(".img-wrapper");
  expect(slideElements.length).toBe(3);
});
