import React from "react";
import { render } from "@testing-library/react";
import App from "./App";

test("render controls", () => {
  const { getAllByText } = render(<App />);
  getAllByText("xPhone").map((e) => expect(e).toBeInTheDocument());
});
