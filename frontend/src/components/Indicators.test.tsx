import React from "react";
import { render } from "@testing-library/react";
import Indicators from "./Indicators";
import dataSlides from "../data-slides";

const progress = 80;

jest.mock("../hooks/useRaf", () => {
  return function useRaf(playSpeed: number, onPlayEnded: () => void) {
    setTimeout(onPlayEnded, playSpeed);
    return progress;
  };
});

beforeEach(() => {
  jest.useFakeTimers();
});

afterEach(() => {
  jest.useRealTimers();
});

describe("验证进度条和幻灯片翻页", () => {
  test("执行回调", () => {
    let currentSlide = 0;
    const playSpeed = 3000;
    const onPlayEnded = () => {
      currentSlide++;
    };

    render(
      <Indicators
        onPlayEnded={onPlayEnded}
        slides={dataSlides}
        currentSlide={currentSlide}
        playSpeed={playSpeed}
      />
    );

    jest.advanceTimersByTime(playSpeed + 100);

    expect(currentSlide).toBe(1);
    expect(document.querySelector(`progress[value="${progress}"]`)).toBeInTheDocument();
  });
});
