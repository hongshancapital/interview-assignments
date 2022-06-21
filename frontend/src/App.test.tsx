import React from "react";
import { render } from "@testing-library/react";
import App from "./App";

test("app rendered", () => {
  const { getByTestId } = render(<App />);

  const appElm = getByTestId("app");
  expect(appElm).toBeVisible();
});
