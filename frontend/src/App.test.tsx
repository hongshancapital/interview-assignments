import React from "react";
import { render } from "@testing-library/react";
import App from "./App";

test("renders commodity carousel", () => {
  const { getByText } = render(<App />);
  const phoneSliceElement = getByText("xPhone");
  expect(phoneSliceElement).toBeInTheDocument();
  const tabletSliceElement = getByText("Tablet");
  expect(tabletSliceElement).toBeInTheDocument();
  const podSliceElement = getByText("Get arPods.");
  expect(podSliceElement).toBeInTheDocument();
});
