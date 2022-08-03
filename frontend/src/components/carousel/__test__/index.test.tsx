import { act, render } from "@testing-library/react";
import { renderHook } from "@testing-library/react-hooks";
import { useInterval, useCarousel } from "../hooks";
import Carousel from "../index";

interface useIntervalInterface {
  callback: () => void;
  delay: number;
}
describe("test useInterval hook", () => {
  const setUp = ({ callback, delay }: useIntervalInterface) =>
    renderHook(() => useInterval(callback, delay));
  jest.useFakeTimers();
  test("interval should work", () => {
    const callback = jest.fn();
    setUp({ callback, delay: 200 });
    expect(callback).not.toBeCalled();
    jest.advanceTimersByTime(700);
    expect(callback).toHaveBeenCalledTimes(3);
  });
});

interface useCarouselInterface {
  N: number;
  delay: number;
}
describe("test useCarousel hook", () => {
  const setUp = ({ N, delay }: useCarouselInterface) =>
    renderHook(() => useCarousel(N, delay));
  jest.useFakeTimers();
  test("slider should get current position", () => {
    const hooks = setUp({ N: 3, delay: 1000 });
    const initSlider = hooks.result.current.slider;
    expect(initSlider).toBe(0);
    act(() => {
      jest.advanceTimersByTime(2500);
    });
    const currentSlider = hooks.result.current.slider;
    expect(currentSlider).toBe(2);
  });
});

describe("test Carousel Component", () => {
  test("render children element", async () => {
    const { container } = render(
      <Carousel>
        <div>1</div>
        <div>2</div>
        <div>3</div>
        <div>4</div>
      </Carousel>
    );
    const carouselChildrenElement = container.querySelector(".carousel-track");
    expect(carouselChildrenElement).toBeInTheDocument();
    expect(carouselChildrenElement?.childElementCount).toBe(4);
  });

  test("carousel play", () => {
    const { container } = render(
      <Carousel duration={2000}>
        <div>apple</div>
        <div>banana</div>
        <div>mangosteen</div>
      </Carousel>
    );
    act(() => {
      jest.advanceTimersByTime(3000);
    });
    expect(container.querySelector(".ele_active")?.textContent).toBe("banana");
    act(() => {
      jest.advanceTimersByTime(2000);
    });
    expect(container.querySelector(".ele_active")?.textContent).toBe(
      "mangosteen"
    );
    act(() => {
      jest.advanceTimersByTime(2000);
    });
    expect(container.querySelector(".ele_active")?.textContent).toBe("apple");
  });
});
