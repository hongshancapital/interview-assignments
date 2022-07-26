import { act, render } from '@testing-library/react';
import React from 'react';
import { Carousel } from './carousel';

describe('Carousel', () => {
  beforeEach(() => {
    jest.useFakeTimers();
  });

  it('should render children', () => {
    const { getByText } = render(
      <Carousel slideDurationMs={100}>
        <div>firstSlide</div>
        <div>secondSlide</div>
        <div>thirdSlide</div>
      </Carousel>
    );

    expect(getByText('firstSlide')).toBeTruthy();
    expect(getByText('secondSlide')).toBeTruthy();
    expect(getByText('thirdSlide')).toBeTruthy();
  });

  it('should render fist slide as active by default', () => {
    const { getByTestId } = render(
      <Carousel slideDurationMs={200}>
        <div>firstSlide</div>
        <div>secondSlide</div>
      </Carousel>
    );

    expect(getByTestId('carousel-slide-0')).toHaveClass('carouselSlide__active');
    expect(getByTestId('carousel-slide-1')).not.toHaveClass('carouselSlide__active');
  });

  it('should render second slide as active after duration ms', () => {
    const slideDurationMs = 200;

    const { getByTestId } = render(
      <Carousel slideDurationMs={slideDurationMs}>
        <div>firstSlide</div>
        <div>secondSlide</div>
      </Carousel>
    );

    act(() => {
      jest.advanceTimersByTime(slideDurationMs);
    });

    expect(getByTestId('carousel-slide-0')).not.toHaveClass('carouselSlide__active');
    expect(getByTestId('carousel-slide-1')).toHaveClass('carouselSlide__active');
  });
});