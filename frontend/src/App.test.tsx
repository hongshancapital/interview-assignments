import React from "react";
import { render } from "@testing-library/react";
import App from "./App";

test("测试能否正常渲染", () => {
  const { getByText } = render(<App />);
  expect(getByText("xPhone")).toBeInTheDocument();
});
