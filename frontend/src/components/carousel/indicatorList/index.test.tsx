import React from "react";
import { render } from "@testing-library/react";
import IndicatorList from ".";

test("子组件数量正确", () => {
  const total = 2;
  
  const { getAllByRole } = render(
    <IndicatorList total={total} currentIndex={0} />
  );

  const count = getAllByRole("listitem").length;
  expect(count).toBe(total);
});
