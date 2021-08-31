import React from "react";
import { render } from "@testing-library/react";
import App from "./App";

test("App render success", () => {
  const { getByRole } = render(<App />);
  const carousel = getByRole("carousel");
  expect(carousel).toBeInTheDocument();
});
