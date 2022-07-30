import React from "react";
import { render } from "@testing-library/react";
import Indicator from ".";

test("非活跃时,为空状态条", () => {
  const { getByTestId } = render(<Indicator active={false} />);
  const bar = getByTestId("indicator-bar");
  expect(bar).toHaveStyle({
    backgroundColor: "transparent",
    right: "100%",
  });
});

test("活跃时,为满状态条", () => {
  const { getByTestId } = render(<Indicator active={true} />);
  const bar = getByTestId("indicator-bar");
  expect(bar).toHaveStyle({
    backgroundColor: "#eaeaea",
    right: 0,
  });
});
