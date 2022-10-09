import React from "react";
import { render, unmountComponentAtNode } from "react-dom";
import { act } from "react-dom/test-utils";

import Carousel from ".";
import carouselItems from "../../data/carousel";

let container: Element | null = null;

beforeEach(() => {
  container = document.createElement("div");
  document.body.append(container);
  jest.useFakeTimers();
});

afterEach(() => {
  if (!container) {
    return;
  }
  unmountComponentAtNode(container);
  container.remove();
  container = null;
  jest.useRealTimers();
});

it('renders <Carousel> component: state 1 (initial)', () => {
  act(() => {
    render(<Carousel items={carouselItems} />, container);
  });

  const items = container?.querySelector<HTMLElement>('.Carousel-items');
  const dots = container?.querySelector<HTMLElement>('.Carousel-dots');

  expect(items?.style.transform).toBe('translateX(-0%)');
  expect(dots?.querySelector(':nth-child(1)')?.classList.contains('Carousel-dot--current')).toBe(true);
  expect(dots?.querySelector(':nth-child(2)')?.classList.contains('Carousel-dot--current')).toBe(false);
  expect(dots?.querySelector(':nth-child(3)')?.classList.contains('Carousel-dot--current')).toBe(false);
});

it('renders <Carousel> component: state 2', () => {
  act(() => {
    render(<Carousel items={carouselItems} />, container);
  });

  const items = container?.querySelector<HTMLElement>('.Carousel-items');
  const dots = container?.querySelector<HTMLElement>('.Carousel-dots');

  act(() => {
    jest.advanceTimersByTime(3000);
  });
  expect(items?.style.transform).toBe('translateX(-100%)');
  expect(dots?.querySelector(':nth-child(1)')?.classList.contains('Carousel-dot--current')).toBe(false);
  expect(dots?.querySelector(':nth-child(2)')?.classList.contains('Carousel-dot--current')).toBe(true);
  expect(dots?.querySelector(':nth-child(3)')?.classList.contains('Carousel-dot--current')).toBe(false);
});

it('renders <Carousel> component: state 3', () => {
  act(() => {
    render(<Carousel items={carouselItems} />, container);
  });

  const items = container?.querySelector<HTMLElement>('.Carousel-items');
  const dots = container?.querySelector<HTMLElement>('.Carousel-dots');

  act(() => {
    jest.advanceTimersByTime(6000);
  });
  expect(items?.style.transform).toBe('translateX(-200%)');
  expect(dots?.querySelector(':nth-child(1)')?.classList.contains('Carousel-dot--current')).toBe(false);
  expect(dots?.querySelector(':nth-child(2)')?.classList.contains('Carousel-dot--current')).toBe(false);
  expect(dots?.querySelector(':nth-child(3)')?.classList.contains('Carousel-dot--current')).toBe(true);
});

it('renders <Carousel> component: state 4 (reset)', () => {
  act(() => {
    render(<Carousel items={carouselItems} />, container);
  });

  const items = container?.querySelector<HTMLElement>('.Carousel-items');
  const dots = container?.querySelector<HTMLElement>('.Carousel-dots');

  act(() => {
    jest.advanceTimersByTime(9000);
  });
  expect(items?.style.transform).toBe('translateX(-0%)');
  expect(dots?.querySelector(':nth-child(1)')?.classList.contains('Carousel-dot--current')).toBe(true);
  expect(dots?.querySelector(':nth-child(2)')?.classList.contains('Carousel-dot--current')).toBe(false);
  expect(dots?.querySelector(':nth-child(3)')?.classList.contains('Carousel-dot--current')).toBe(false);
});
