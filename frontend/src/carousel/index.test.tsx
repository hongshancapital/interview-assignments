import React from "react";
import { act, fireEvent, render } from "@testing-library/react";
import { Carousel, CarouselRef } from "./index";

beforeEach(() => {
  jest.useFakeTimers();
});

afterEach(() => {
  jest.useRealTimers();
});

const list = [
  {
    id: "1",
    element: <div>1</div>,
  },
  {
    id: "2",
    element: <div>2</div>,
  },
  {
    id: "3",
    element: <div>3</div>,
  },
];

describe("Test Carousel", () => {
  const duration = 2000;

  it("render", () => {
    const { getByText } = render(<Carousel duration={duration} list={list} />);
    expect(getByText("3")).toBeInTheDocument();
    setTimeout(() => {
      expect(getByText("2")).toBeInTheDocument();
    }, duration);
  });

  it("props", () => {
    const ref = React.createRef<CarouselRef>();
    render(<Carousel ref={ref} duration={1000} list={list} speed={0.5} />);
    expect(ref.current?.instance.duration).toBe(1000);
    expect(ref.current?.instance.speed).toBe(0.5);
    expect(ref.current?.instance.list).toBe(list);
  });
});

export const test = "test";
