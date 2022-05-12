import { act, render } from "@testing-library/react";
import { renderHook } from "@testing-library/react-hooks";
import Carousel, { CarouselProps } from "..";
import CarouselItem from "../Item";
import { list } from "../../mock";
import { useAutoPlay } from "../hooks/useAutoplay";

const createDemo = (props?: CarouselProps) => (
  <Carousel {...props}>
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

    const { result } = renderHook(() =>
      useAutoPlay({ autoPlay: true, interval: 3000, count: 3 })
    );

    expect(result.current.activeIndex).toBe(0);
    act(() => {
      jest.advanceTimersByTime(3000);
    });
    expect(result.current.activeIndex).toBe(1);
    act(() => {
      jest.advanceTimersByTime(3000);
    });
    expect(result.current.activeIndex).toBe(2);
    act(() => {
      jest.advanceTimersByTime(3000);
    });
    expect(result.current.activeIndex).toBe(0);
  });

  it("Should `autoPlay: false` works correctly", () => {
    jest.useFakeTimers();

    const { result } = renderHook(() =>
      useAutoPlay({ autoPlay: false, interval: 3000, count: 3 })
    );

    expect(result.current.activeIndex).toBe(0);
    act(() => {
      jest.advanceTimersByTime(3000);
    });
    expect(result.current.activeIndex).toBe(0);
  });

  it("Should render correctly when children is empty", () => {
    expect(() => {
      render(<Carousel />);
    }).not.toThrow();
  });
});
