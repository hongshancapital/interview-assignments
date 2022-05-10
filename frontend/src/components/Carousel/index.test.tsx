import React from 'react';
import { render, act } from '@testing-library/react';
import Carousel from './index';
import { carouselItems } from '../../App';

describe('Carousel', () => {
  const { getByText, container, unmount } = render(<Carousel items={carouselItems} />);
  test('Carousel render ok', () => {
    const carouselItem1 = getByText(/Lots to love/i);
    const carouselItem2 = getByText(/Just the right amount of everything./i);
    const carouselItem3 = getByText(/Get airPods/i);
    expect(carouselItem1).toBeInTheDocument();
    expect(carouselItem2).toBeInTheDocument();
    expect(carouselItem3).toBeInTheDocument();
  });

  test('Carousel auto play', () => {
    jest.useFakeTimers();
    const spySetInterval = jest.spyOn(global, 'setInterval');
    render(<Carousel items={carouselItems} />);
    act(() => {
      jest.runOnlyPendingTimers();
    });
    expect(spySetInterval).toHaveBeenCalled();
    expect(spySetInterval).toHaveBeenCalledTimes(1);
  });

  test('Carousel close auto play and start from 1', () => {
    const { container } = render(<Carousel autoPlay={false} items={carouselItems} startIndex={1} duration={2000} />);
    const domNode = container.getElementsByClassName('carousel-list')[0];
    const { transform } = window.getComputedStyle(domNode);
    expect(transform).toBe(`translateX(${(-1 / 3) * 100}%)`);
  });

  test('Carousel hide Indicators', () => {
    const { container } = render(<Carousel items={carouselItems} showIndicators={false} />);
    const domNode = container.getElementsByClassName('carousel-indicators')[0];
    expect(domNode).toBeUndefined();
  });

  test('Carousel unmount', () => {
    unmount?.();
    expect(container.innerHTML).toBe('');
  });
});