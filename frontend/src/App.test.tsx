import React from "react";
import { act, render } from "@testing-library/react";
import App from "./App";

test("renders learn react link", async () => {
  let ctn = document.createElement("div");
  document.body.appendChild(ctn);
  act(() => {
    const { getByText } = render(<App/>, {
      container: ctn
    });
    const titleElement = getByText(/arPods/i);
    expect(titleElement).toBeInTheDocument();
  });

  let el1 = ctn.querySelector(".current");
  await new Promise((resolve) => {
    setTimeout(resolve, 3100);
  });
  let el2 = ctn.querySelector(".current");
  expect(el1).not.toEqual(el2)
});
