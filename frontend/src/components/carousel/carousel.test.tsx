/**
 * @jest-environment jsdom
 */

import React from 'react';
import { act, fireEvent, render, screen } from '@testing-library/react';
import Carousel from './carousel';
import { CarouselProps } from './types';

// Fake timers using Jest
beforeEach(() => {
  jest.useFakeTimers();
});

// Running all pending timers and switching to real timers using Jest
afterEach(() => {
  jest.runOnlyPendingTimers();
  jest.useRealTimers();
});

describe('Carousel', () => {
  const renderCarousel = ({
    slideCount = 5,
    ...props
  }: CarouselProps & { slideCount?: number } = {}) =>
    render(
      <Carousel {...props}>
        {[...Array(slideCount)].map((_, index) => (
          <img src="#" alt={`slide ${index}`} key={index} />
        ))}
      </Carousel>
    );

  it('should check slides length testing', () => {
    renderCarousel({ slideCount: 3 });
    const slides = document.querySelectorAll('.slider-list .slide');
    expect(slides.length).toBe(3);
  });

  it('should autoplay control testing', () => {
    const beforeSlide = jest.fn();
    const afterSlide = jest.fn();
    const speed = 500;
    const autoplayInterval = 3000;
    const slideCount = 3;

    renderCarousel({
      slideCount,
      autoplayInterval,
      autoplay: true,
      loop: true,
      speed,
      beforeSlide,
      afterSlide,
    });

    expect(beforeSlide).toHaveBeenCalledTimes(1);
    expect(afterSlide).toHaveBeenCalledTimes(0);

    act(() => {
      jest.advanceTimersByTime(autoplayInterval);
    });

    expect(beforeSlide).toHaveBeenCalledTimes(2);
    expect(afterSlide).toHaveBeenCalledTimes(1);
  });

  it('should has next, prev control testing', async () => {
    renderCarousel({ slideCount: 3, slideIndex: 0 });
    const next = screen.getByText('Next');
    await fireEvent.click(next);
    expect(document.querySelectorAll('.slider-list .slide')[1]).toHaveClass(
      'slide-current'
    );

    const prev = screen.getByText('Prev');
    await fireEvent.click(prev);
    expect(document.querySelectorAll('.slider-list .slide')[0]).toHaveClass(
      'slide-current'
    );
  });
});
