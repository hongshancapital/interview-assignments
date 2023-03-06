import React from 'react';
import "@testing-library/jest-dom/extend-expect";
import { render, fireEvent, act } from "@testing-library/react";
import Carousel from './components/Carousel';


describe("Carousel", () => {
  const items = [
    { title: "Item 1", describe: "Description 1", img: "/item1.jpg" },
    { title: "Item 2", describe: "Description 2", img: "/item2.jpg" },
    { title: "Item 3", describe: "Description 3", img: "/item3.jpg" },
  ];

  jest.useFakeTimers();

  test("renders correctly", () => {
    const { container } = render(<Carousel items={items} duration={3000} speed={500} />);
    const carousel = container.querySelector(".carousel");
    expect(carousel).toBeInTheDocument();
  });

  test("renders nothing when items array is empty", () => {
    const { queryByTestId } = render(<Carousel items={[]} duration={3000} speed={500} />);
    const carousel = queryByTestId("carousel");
    expect(carousel).not.toBeInTheDocument();
  });

  test("renders nothing when items array is empty", () => {
    const { queryByTestId } = render(<Carousel items={[]} duration={3000} speed={500} />);
    const carousel = queryByTestId("carousel");
    expect(carousel).not.toBeInTheDocument();
  });

  test("renders nothing when items array is null", () => {
    const { queryByTestId } = render(<Carousel items={null} duration={3000} speed={500} />);
    const carousel = queryByTestId("carousel");
    expect(carousel).not.toBeInTheDocument();
  });

  test("slides correctly", () => {
    const { getByText } = render(<Carousel items={items} duration={3000} speed={500} />);

    // wait for the first slide
    expect(getByText("Item 1")).toBeInTheDocument();
    act(() => jest.advanceTimersByTime(3000));

    // check the second slide
    expect(getByText("Item 2")).toBeInTheDocument();
    act(() => jest.advanceTimersByTime(3000));

    // check the third slide
    expect(getByText("Item 3")).toBeInTheDocument();
    act(() => jest.advanceTimersByTime(3000));

    // check the first slide again
    expect(getByText("Item 1")).toBeInTheDocument();
  });

  test("switches slides correctly on tab clicks", () => {
    const { container, getByText } = render(<Carousel items={items} duration={3000} speed={500} />);
    const carouselTabs = container.querySelectorAll(".carousel-tab");
    const tab2 = carouselTabs[1];
    const tab3 = carouselTabs[2];

    // wait for the first slide
    expect(getByText("Item 1")).toBeInTheDocument();

    // click the second tab
    fireEvent.click(tab2);

    // check the second slide
    expect(getByText("Item 2")).toBeInTheDocument();

    // click the thrid tab
    fireEvent.click(tab3);

    // check the thrid slide
    expect(getByText("Item 3")).toBeInTheDocument();

  });

});
