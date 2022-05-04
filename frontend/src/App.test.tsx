import React from "react";
import { render } from "@testing-library/react";
import App from "./App";

test("render app", () => {
  const { getByTestId } = render(<App />);
  const appElement = getByTestId("App");
  expect(appElement).toBeInTheDocument();
});
