import React from "react";
import { renderHook } from "@testing-library/react-hooks";
import { act, render, screen, fireEvent } from "@testing-library/react";
import { useActiveIndex } from "./hooks/useActiveIndex";
import CarouselItem from "./components/carousel-item/index";
import Progress from "./components/progress/index";
import { carousels } from "../../constants/index";
import Carousel from "./index";

jest.useFakeTimers();

describe("carousel test", () => {
  test("useActiveIndex test", () => {
    const { result } = renderHook(() =>
      useActiveIndex({ count: carousels.length })
    );
    expect(result.current[0]).toBe(0);
    act(() => {
      jest.runOnlyPendingTimers();
    });
    expect(result.current[0]).toBe(1);
    act(() => {
      jest.runOnlyPendingTimers();
    });
    expect(result.current[0]).toBe(2);
    act(() => {
      result.current[1](1);
    });
    expect(result.current[0]).toBe(1);
    jest.clearAllTimers();
  });

  it("carousel-item test", () => {
    const props = {
      id: "carousel-001",
      src: "",
      title: ["xPhone"],
      desc: ["Lots to love. Less to spend.", "Starting at $399."],
    };
    act(() => {
      render(<CarouselItem {...props} />);
    });

    props.title.forEach((c) => {
      const title = screen.getByText(c);
      expect(title).toBeInTheDocument();
    });
    props.desc?.forEach((c) => {
      const desc = screen.getByText(c);
      expect(desc).toBeInTheDocument();
    });
  });

  it("progress test", () => {
    const fn = jest.fn();
    const props = {
      items: carousels,
      interval: 3000,
      activeIndex: 0,
      callback: fn,
    };

    act(() => {
      render(<Progress {...props} />);
    });

    const elem = document.querySelector(".progress-view");
    expect(elem).toBeInTheDocument();

    const items = document.querySelectorAll(".progress-view");
    expect(items.length).toBe(props.items.length);

    act(() => {
      fireEvent.click(items[1]);
    });
    expect(fn).toHaveBeenCalled();
    expect(props?.callback).toHaveBeenCalledTimes(1);
  });

  it("carousel test", () => {
    const props = { items: carousels };
    act(() => {
      render(<Carousel {...props} />);
    });

    const item = document.querySelector(".item-container");
    expect(item).toBeInTheDocument();

    const items = document.querySelectorAll(".item-container");
    expect(items.length).toBe(props.items.length);
  });
});
