import React from "react";
import { renderHook, render, screen, act } from "@testing-library/react";
import Carousel from "./index";
// import { useCarousel } from "./useCarousel";

describe("render carousel", () => {
  test("carousel item should not be rendered with zero child", () => {
    render(<Carousel></Carousel>);
    expect(screen.getByTestId("carousel")).toBeInTheDocument();
    expect(screen.queryAllByTestId("carousel-item")).toHaveLength(0);
    expect(screen.queryAllByTestId("carousel-indicator")).toHaveLength(0);
  });

  test("carousel item should be rendered with children", () => {
    render(
      <Carousel></Carousel>
    );
    expect(screen.getByTestId("carousel")).toBeInTheDocument();
    expect(screen.queryAllByTestId("carousel-item")).toHaveLength(2);
    expect(screen.queryAllByTestId("carousel-indicator")).toHaveLength(2);
    expect(screen.getByText("test1")).toBeInTheDocument();
    expect(screen.getByText("test2")).toBeInTheDocument();
  });
});

// describe("useCarousel", () => {
//   test("stepToNext function should set curIndex to next number if not out of bound", () => {
//     const { result } = renderHook(() => useCarousel(3, 3000, 1000));
//     const stepToNext = result.current.stepToNext;
//     expect(result.current.curIndex).toBe(0);
//     act(() => {
//       stepToNext();
//     });
//     expect(result.current.curIndex).toBe(1);
//     act(() => {
//       stepToNext();
//     });
//     expect(result.current.curIndex).toBe(2);
//   });
//   test("stepToNext function should set curIndex to zero if  out of bound", () => {
//     const { result } = renderHook(() => useCarousel(2, 3000, 1000));
//     const stepToNext = result.current.stepToNext;
//     expect(result.current.curIndex).toBe(0);
//     act(() => {
//       stepToNext();
//     });
//     expect(result.current.curIndex).toBe(1);
//     act(() => {
//       stepToNext();
//     });
//     expect(result.current.curIndex).toBe(0);
//   });

//   test.each([
//     [3, 1, 1],
//     [1, 3, 1],
//   ])(
//     "minTransitionTime is equal to the minimum of [duration,transitionTime]",
//     (duration, transitionTime, expected) => {
//       const { result } = renderHook(() =>
//         useCarousel(2, duration, transitionTime)
//       );
//       expect(result.current.minTransitionTime).toBe(expected);
//     }
//   );
// });