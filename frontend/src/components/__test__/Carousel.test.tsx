import React from "react";
import Carousel from "../Carousel";
import { render } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { dataList } from "../../App";
import carouselStyle from "@/components/Carousel/index.module.scss";
import scrollBoxStyle from "@/components/Carousel/ScrollBox.module.scss";

test("测试 Carousel", () => {
  const { container } = render(<Carousel dataList={dataList} delay={3000} />);
  const count = dataList.length;

  /* 控制器检查 */
  const controlBox = container.querySelector(`.${carouselStyle.control_box}`);
  expect(controlBox).toBeTruthy();
  expect(
    controlBox?.querySelector(`.${carouselStyle.color_block_animation}`)
  ).toBeTruthy();
  expect(controlBox?.childElementCount).toEqual(count);

  /* 图片检查 */
  const scrollBox = container.querySelector(`.${scrollBoxStyle.scroll_box}`);
  expect(scrollBox).toBeTruthy();
  expect(scrollBox?.childElementCount).toEqual(count);

  /* 图片检查 */
  const controlItem = controlBox?.childNodes?.[count - 1];
  expect(controlItem).toBeTruthy();
  userEvent.click(controlItem as HTMLElement);
  const color_block_stop = controlBox?.querySelector(
    `.${carouselStyle.color_block_stop}`
  ) as HTMLDivElement;
  expect(color_block_stop).toBeTruthy();
});
