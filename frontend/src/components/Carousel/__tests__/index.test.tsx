import { act, render } from "@testing-library/react";
import React from "react";
import Carousel, { CarouselRef } from "../Carousel";

describe("test Carousel", () => {
  // mock定时器
  beforeEach(() => {
    jest.useFakeTimers();
  });

  afterEach(() => {
    jest.useRealTimers();
  });

  it("render Carousel", () => {
    const { getByText } = render(
      <Carousel deply={2000}>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    );
    expect(getByText("3")).toBeInTheDocument();
    setTimeout(() => {
      expect(getByText("2")).toBeInTheDocument();
    }, 2000);
  });

  it("should has start, stop and goTo function", async () => {
    const ref = React.createRef<CarouselRef>();
    render(
      <Carousel ref={ref}>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    );
    const { start, stop, goTo } = ref.current || {};
    expect(typeof start).toBe("function");
    expect(typeof stop).toBe("function");
    expect(typeof goTo).toBe("function");
    act(() => {
      ref.current?.goTo(2);
    });
    expect(ref.current?.instance.curIndex).toBe(2);
  });

  it("should work for deply time", () => {
    const ref = React.createRef<CarouselRef>();
    render(
      <Carousel ref={ref} deply={3000}>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    );
    expect(ref.current?.instance.deply).toBe(3000);
  });

  it("should work for speed time", () => {
    const { container } = render(
      <Carousel speed={1}>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    );
    expect(container.querySelector(".wrap")).toHaveStyle({
      transition: "left 1s linear",
    });
  });

  it("should carousel set width ", () => {
    const { container } = render(
      <Carousel width={300}>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    );
    expect(container.querySelector(".carousel")).toHaveStyle({
      width: "300px",
    });
  });

  it("should set dots speed time", () => {
    const { container } = render(
      <Carousel deply={2000}>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    );
    expect(container.querySelectorAll(".active")[0]).toHaveStyle({
      animation: "progress 2000ms linear",
    });
  });
});
