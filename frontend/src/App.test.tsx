import React from 'react';
import { render } from '@testing-library/react';
import { act } from "react-dom/test-utils";
import App from './App';
import Carousel from './components/Carousel';
import defaultConfig from './mock/config';
console.log('test---------------------');

describe("Carousel", () => {
  it("check the container", () => {
    const { container } = render(<App />);
    const appElem = container.querySelector(".App");
    const carouselElem = container.querySelector(".carousel-wrap");
    expect(carouselElem).not.toBeNull();
    expect(appElem).toContainHTML(carouselElem!.innerHTML);
  });
  it("check carousel render", () => {
    jest.useFakeTimers();
    const { container } = render(<App />);
    const box = container.querySelector(".carousel-content");
    expect(box).toHaveStyle(` transform: translateX(${-0 * 100}%)`);

    act(() => {
      jest.advanceTimersByTime(3000);
    });

    expect(box).toHaveStyle(` transform: translateX(${-1 * 100}%)`);

    act(() => {
      jest.advanceTimersByTime(3000);
    });

    expect(box).toHaveStyle(` transform: translateX(${-2 * 100}%)`);
  });
});

describe("Carousel", () => {
  it("check every item's title and desc", () => {
    const { getByText } = render(<Carousel {...defaultConfig} />);

    defaultConfig.items.forEach((item) => {
      item.title.forEach((c) => {
        const title = getByText(c);
        expect(title).toBeInTheDocument();
      });
      item.desc?.forEach((c) => {
        const desc = getByText(c);
        expect(desc).toBeInTheDocument();
      });
    });
  });

  it("check indicator component", () => {
    const { container } = render(<Carousel {...defaultConfig} />);
    const indicatorElem = container.querySelector(".carousel-indicator");
    expect(indicatorElem).toBeInTheDocument();

    const itemElem = indicatorElem?.querySelector(".item");
    expect(itemElem).toBeInTheDocument();
  });
});
