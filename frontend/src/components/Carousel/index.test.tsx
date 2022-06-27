import React from 'react';
import { render, act } from '@testing-library/react';
import Carousel from './index';
import { CAROUSEL_LIST } from '../../constants';

beforeEach(() => {
  jest.useRealTimers();
});

describe('Carousel Component Test', () => {
  test('init render', () => {
    const { container } = render(<Carousel list={CAROUSEL_LIST}></Carousel>);
    expect(container.querySelector('div')).toHaveClass('carousel');
    expect(container.querySelectorAll('.carouselItems').length).toBe(3);
    expect(container.querySelectorAll('.dot__item').length).toBe(3);
  });

  test('Test Carousel Autoplay', () => {
    jest.useFakeTimers();
    const { container } = render(<Carousel list={CAROUSEL_LIST}></Carousel>);
    act(() => {
      jest.advanceTimersByTime(3000);
    });

    expect(container.querySelector('.carousel__container')).toHaveStyle(
      'transform: translateX(-100vw)',
    );

    act(() => {
      jest.advanceTimersByTime(3000);
    });

    expect(container.querySelector('.carousel__container')).toHaveStyle(
      'transform: translateX(-200vw)',
    );

    act(() => {
      jest.advanceTimersByTime(3000);
    });

    expect(container.querySelector('.carousel__container')).toHaveStyle(
      'transform: translateX(-0vw)',
    );
  });
});
