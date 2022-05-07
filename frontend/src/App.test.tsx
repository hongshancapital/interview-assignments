import React from "react";
import { render } from "@testing-library/react";

import App from "./App";

describe("test App", () => {
  beforeEach(() => {
    global.ResizeObserver = jest.fn().mockImplementation(() => ({
      observe: jest.fn(),
      unobserve: jest.fn(),
      disconnect: jest.fn(),
    }));
    jest.useFakeTimers();
  });

  test("render board count correctly", () => {
    const { container } = render(<App />);
    expect(container.querySelectorAll(".board").length).toBe(4);
  });
});
