import React from "react";
import { render } from "@testing-library/react";
import Carousel from "./Carousel";
import CarouseItem from "../carouselItem/CarouselItem";

describe("轮播图组件", () => {
  it("渲染三个子组件", () => {
    const wrapper = render(
      <Carousel>
        <CarouseItem>1</CarouseItem>
        <CarouseItem>2</CarouseItem>
        <CarouseItem>3</CarouseItem>
      </Carousel>
    );

    const carouselListEl = wrapper.container.querySelector(".carousel-list");
    expect(carouselListEl).toBeInTheDocument();
    expect(carouselListEl?.childElementCount).toBe(3);
  });
});
