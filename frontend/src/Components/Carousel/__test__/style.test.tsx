import React from "react";
import renderer from "react-test-renderer";
import "jest-styled-components";
import { CarouselWrapper, LoopContainer, LoopItem, Indicator } from "../style";

describe("Carousel Style", () => {
  test("render CarouselWrapper", () => {
    const wrapper = renderer.create(<CarouselWrapper />).toJSON();
    expect(wrapper).toHaveStyleRule("width", "100%");
    expect(wrapper).toHaveStyleRule("height", "100%");
    expect(wrapper).toHaveStyleRule("overflow", "hidden");
    expect(wrapper).toHaveStyleRule("position", "relative");
  });

  test("render LoopContainer", () => {
    const container = renderer
      .create(<LoopContainer total={3} current={0} slideSpeed={200} />)
      .toJSON();

    expect(container).toHaveStyleRule("display", "flex");
    expect(container).toHaveStyleRule("height", "100%");
    expect(container).toHaveStyleRule("animation-fill-mode", "both");
    
    expect(container).toHaveStyleRule("width", "300%");
    expect(container).toHaveStyleRule("animation-duration", "200ms");
  });

  test("render LoopItem", () => {
    const container = renderer
      .create(<LoopItem total={4} />)
      .toJSON();

    expect(container).toHaveStyleRule("display", "inline-block");
    expect(container).toHaveStyleRule("height", "100%");
    expect(container).toHaveStyleRule("width", "25%");
  });

  test("render Indicator", () => {
    const container = renderer
      .create(<Indicator />)
      .toJSON();

    expect(container).toHaveStyleRule("position", "absolute");
    expect(container).toHaveStyleRule("left", "50%");
    expect(container).toHaveStyleRule("transform", "translateX(-50%)");
    expect(container).toHaveStyleRule("bottom", "20px");
    expect(container).toHaveStyleRule("margin", "auto");
    expect(container).toHaveStyleRule("font-size", "0");
  });

});
