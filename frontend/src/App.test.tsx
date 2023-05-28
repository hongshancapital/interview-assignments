import React from "react";
import { render, screen, act } from "@testing-library/react";
import Carousel, { CarouselItemType } from "./components/Carousel";

const mockData: CarouselItemType[] = [
  {
    imageUrl: require("./assets/iphone.png"),
    title: "Title 1",
    content: "Content 1",
  },
  {
    imageUrl: require("./assets/tablet.png"),
    title: "Title 2",
    content: "Content 2",
  },
  { imageUrl: require("./assets/airpods.png"), title: "Title 3" },
];

describe("Carousel component", () => {
  afterEach(() => {
    jest.restoreAllMocks();
    jest.useRealTimers();
  });

  it("renders correctly", () => {
    render(<Carousel data={mockData} />);
    expect(screen.getByText("Title 1")).toBeInTheDocument();
    expect(screen.getByText("Content 1")).toBeInTheDocument();
    expect(screen.getByText("Title 2")).toBeInTheDocument();
    expect(screen.getByText("Content 2")).toBeInTheDocument();
    expect(screen.getByText("Title 3")).toBeInTheDocument();
  });

  it("auto plays carousel", () => {
    jest.useFakeTimers();
    render(<Carousel data={mockData} />);
    const indicatorActiveCorrect = (index: number) => {
      const indicatorVisibility = window
          .getComputedStyle(screen.getByTestId(`indicator-${index}`))
          .getPropertyValue("visibility");
      expect(indicatorVisibility === "visible").toBe(true);
    };
    act(() => {
      jest.advanceTimersByTime(3500);
    });
    indicatorActiveCorrect(1);
    act(() => {
      jest.advanceTimersByTime(3000);
    });
    indicatorActiveCorrect(2);
    act(() => {
      jest.advanceTimersByTime(3000);
    });
    indicatorActiveCorrect(0);
  });
});
