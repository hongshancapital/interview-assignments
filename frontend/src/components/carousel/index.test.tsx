import React from "react";
import { render } from "@testing-library/react";
import { act } from "react-dom/test-utils";

import Carousel from ".";
beforeEach(() => {
  jest.useFakeTimers();
});

test("各项元素正常渲染", () => {
  const { getByText, getByTestId } = render(<Carousel />);
  const iphoneBannerElement = getByText(/^xPhone$/);
  expect(iphoneBannerElement).toBeInTheDocument();
  const tabletBannerElement = getByText(/^Tablet$/);
  expect(tabletBannerElement).toBeInTheDocument();
  const airPodsBannerElement = getByText(
    /^Buy a Table or xPhone for college\.$/
  );
  expect(airPodsBannerElement).toBeInTheDocument();

  /** 3秒后，轮播图滚动一帧 */
  act(() => {
    jest.advanceTimersByTime(3000);
  });
  const iphone = getByTestId("banner-list");
  expect(iphone).toHaveStyle("left:-100%");

  /** 再3秒后，轮播图再滚动一帧 */
  act(() => {
    jest.advanceTimersByTime(3000);
  });
  expect(iphone).toHaveStyle("left:-200%");

  /** 再3秒后，轮播图回到首帧 */
  act(() => {
    jest.advanceTimersByTime(3000);
  });
  expect(iphone).toHaveStyle("left:-000%");
});
