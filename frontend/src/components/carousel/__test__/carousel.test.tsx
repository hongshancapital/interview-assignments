import React from "react";
import { render, fireEvent } from "@testing-library/react";
import Carousel, { CarouselItem } from "../index";

test("测试是否能正常渲染和跳转", () => {
  const { container, getByText } = render(
    <Carousel>
      <CarouselItem>
        <div>1</div>
      </CarouselItem>
      <CarouselItem>
        <div>2</div>
      </CarouselItem>
      <CarouselItem>
        <div>3</div>
      </CarouselItem>
    </Carousel>
  );
  expect(getByText("1")).toBeInTheDocument();
  let buttomArray = container.getElementsByTagName("li");

  fireEvent.click(buttomArray[2]);
  expect(buttomArray[2].getElementsByTagName("span")[0].className).toEqual(
    "slick-active"
  );
});
