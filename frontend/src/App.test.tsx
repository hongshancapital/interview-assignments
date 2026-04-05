/*
 * @Description: 
 * @Version: 1.0
 * @Autor: zhangw
 * @Date: 2023-04-07 18:11:39
 * @LastEditors: zhangw
 * @LastEditTime: 2023-04-10 23:21:12
 */
import React from "react";
import {
  render,
  screen,
  waitFor
} from "@testing-library/react";
import App from "./App";

test("carousel render test", async () => {
  render(<App />);
  expect(await screen.findByTestId("carousel-item-iphone")).toBeInTheDocument();
  screen.debug();
  const tabletItem = await waitFor(
    async () => await screen.findByTestId("carousel-item-tablet"),
    {
      timeout: 4000
    }
  );
  expect(tabletItem).toBeInTheDocument();
  screen.debug();
  const airpodsItem = await waitFor(
    async () => await screen.findByTestId("carousel-item-airpods"),
    {
      timeout: 4000
    }
  );
  expect(airpodsItem).toBeInTheDocument();
  screen.debug();
});
