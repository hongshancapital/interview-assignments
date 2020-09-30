import React from "react";
import { render } from "@testing-library/react";
import App from "./App";

test("should renders children", () => {
  const { getAllByRole } = render(<App />);
  const headings = getAllByRole("heading", {
    level: 3,
  });
  expect(headings).toHaveLength(4);
});
