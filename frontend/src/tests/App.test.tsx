import React from "react";
import { render } from "@testing-library/react";
import App from "../components/App";

test('App render', () => {
  const { queryAllByText } = render(<App />);
  const node = queryAllByText('xPhone');
  expect(node).toBeTruthy();
});
