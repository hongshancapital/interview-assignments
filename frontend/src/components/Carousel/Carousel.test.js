import React from "react";
import { render, unmountComponentAtNode } from "react-dom";
import { act } from "react-dom/test-utils";
import pretty from "pretty";

import Carousel from "./index";

import airpodsImg from "../../assets/airpods.png";
import iphoneImg from "../../assets/iphone.png";
import tabletImg from "../../assets/tablet.png";

jest.useFakeTimers();

let container = null;
beforeEach(() => {
  container = document.createElement("div");
  document.body.appendChild(container);
});

afterEach(() => {
  unmountComponentAtNode(container);
  container.remove();
  container = null;
});

it("定时切换正常", () => {
  const INTERVAL = 3000;
  act(() => {
    render(
      <Carousel
        imgList={[iphoneImg, tabletImg, airpodsImg]}
        interval={INTERVAL}
      />,
      container
    );
  });
  act(() => {
    jest.advanceTimersByTime(100);
  });
  expect(
    pretty(container.innerHTML)
  ).toMatchInlineSnapshot(`
    "<div class=\\"carousel\\">
      <div class=\\"picture-wrap\\" style=\\"left: 0%;\\"><img class=\\"picture\\" src=\\"iphone.png\\"><img class=\\"picture\\" src=\\"tablet.png\\"><img class=\\"picture\\" src=\\"airpods.png\\"></div>
      <div class=\\"indicator-wrap\\">
        <div class=\\"indicator active\\">
          <div class=\\"indicator-progress\\" style=\\"transition-duration: 3000ms;\\"></div>
        </div>
        <div class=\\"indicator \\">
          <div class=\\"indicator-progress\\" style=\\"transition-duration: 3000ms;\\"></div>
        </div>
        <div class=\\"indicator \\">
          <div class=\\"indicator-progress\\" style=\\"transition-duration: 3000ms;\\"></div>
        </div>
      </div>
    </div>"
  `);

  jest.advanceTimersByTime(INTERVAL + 1000);
  expect(
    pretty(container.innerHTML)
  ).toMatchInlineSnapshot(`
    "<div class=\\"carousel\\">
      <div class=\\"picture-wrap\\" style=\\"left: 0%;\\"><img class=\\"picture\\" src=\\"iphone.png\\"><img class=\\"picture\\" src=\\"tablet.png\\"><img class=\\"picture\\" src=\\"airpods.png\\"></div>
      <div class=\\"indicator-wrap\\">
        <div class=\\"indicator active\\">
          <div class=\\"indicator-progress\\" style=\\"transition-duration: 3000ms;\\"></div>
        </div>
        <div class=\\"indicator \\">
          <div class=\\"indicator-progress\\" style=\\"transition-duration: 3000ms;\\"></div>
        </div>
        <div class=\\"indicator \\">
          <div class=\\"indicator-progress\\" style=\\"transition-duration: 3000ms;\\"></div>
        </div>
      </div>
    </div>"
  `);

  expect(
    pretty(container.innerHTML)
  ).toMatchInlineSnapshot(`
    "<div class=\\"carousel\\">
      <div class=\\"picture-wrap\\" style=\\"left: 0%;\\"><img class=\\"picture\\" src=\\"iphone.png\\"><img class=\\"picture\\" src=\\"tablet.png\\"><img class=\\"picture\\" src=\\"airpods.png\\"></div>
      <div class=\\"indicator-wrap\\">
        <div class=\\"indicator active\\">
          <div class=\\"indicator-progress\\" style=\\"transition-duration: 3000ms;\\"></div>
        </div>
        <div class=\\"indicator \\">
          <div class=\\"indicator-progress\\" style=\\"transition-duration: 3000ms;\\"></div>
        </div>
        <div class=\\"indicator \\">
          <div class=\\"indicator-progress\\" style=\\"transition-duration: 3000ms;\\"></div>
        </div>
      </div>
    </div>"
  `);
});
