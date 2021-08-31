import React from "react";
import { render } from "@testing-library/react";
import Bar from "./Bar";

test("onFinish sucess", async () => {
  const { getByTitle } = render(
    <Bar
      active={true}
      key="bar"
      attr={{
        title: "测试标题",
      }}
    />
  );

  const el = getByTitle("测试标题");
  expect(el).toBeInTheDocument();
});
