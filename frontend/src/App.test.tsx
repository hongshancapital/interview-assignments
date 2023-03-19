import React from "react";
import { render } from "@testing-library/react";
import App from "./App";

describe("测试App组件", () => {
  test("测试渲染是否正常", () => {
    const { container } = render(<App />);
    const Apps = container.querySelectorAll(".App");
    expect(Apps.length).toBe(1);
  });
});
