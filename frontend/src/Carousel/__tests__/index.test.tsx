import { act, render } from "@testing-library/react";
import Carousel, { CarouselProps, CarouselRef } from "..";
import CarouselItem from "../Item";
import { list } from "../../mock";
import React from "react";

const createDemo = (
  props?: CarouselProps,
  ref?: React.RefObject<CarouselRef>
) => (
  <Carousel {...props} ref={ref}>
    {list.map((item) => (
      <CarouselItem data={item} key={item.id} />
    ))}
  </Carousel>
);

describe("Carousel", () => {
  it("Should mount/unmount correctly", () => {
    const demo = createDemo();
    const { unmount, rerender } = render(demo);

    expect(() => {
      rerender(demo);
      unmount();
    }).not.toThrow();
  });

  it("Should `autoPlay: true` works correctly", () => {
    jest.useFakeTimers();

    const ref = React.createRef<CarouselRef>();
    const demo = createDemo({ autoPlay: true }, ref);
    render(demo);

    expect(ref.current?.activeIndex).toBe(0);
    act(() => {
      jest.advanceTimersByTime(3000);
    });
    expect(ref.current?.activeIndex).toBe(1);
    act(() => {
      jest.advanceTimersByTime(3000);
    });
    expect(ref.current?.activeIndex).toBe(2);
    act(() => {
      jest.advanceTimersByTime(3000);
    });
    expect(ref.current?.activeIndex).toBe(0);
  });

  it("Should `autoPlay: false` works correctly", () => {
    jest.useFakeTimers();

    const ref = React.createRef<CarouselRef>();
    const demo = createDemo({ autoPlay: false }, ref);
    render(demo);

    expect(ref.current?.activeIndex).toBe(0);
    act(() => {
      jest.advanceTimersByTime(3000);
    });
    expect(ref.current?.activeIndex).toBe(0);
  });

  it("Should render correctly when children is empty", () => {
    expect(() => {
      render(<Carousel />);
    }).not.toThrow();
  });
});
