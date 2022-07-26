import { act, getByTestId, render } from '@testing-library/react';
import React from 'react';
import { Carousel } from './Carousel';

describe('Carousel', () => {
  beforeEach(() => {
    jest.useFakeTimers();
  });

  it('should render slides and indicators', () => {
    const { getByText, getByTestId } = render(
      <Carousel slideDurationMs={100}>
        <div>firstSlide</div>
        <div>secondSlide</div>
        <div>thirdSlide</div>
      </Carousel>
    );

    expect(getByText('firstSlide')).toBeTruthy();
    expect(getByText('secondSlide')).toBeTruthy();
    expect(getByText('thirdSlide')).toBeTruthy();

    expect(getByTestId('carousel-indicator-0')).toBeTruthy();
    expect(getByTestId('carousel-indicator-1')).toBeTruthy();
    expect(getByTestId('carousel-indicator-2')).toBeTruthy();
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

    expect(getByTestId('carousel-indicator-0')).toHaveClass('carouselIndicator__active');
    expect(getByTestId('carousel-indicator-1')).not.toHaveClass('carouselIndicator__active');
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

    expect(getByTestId('carousel-indicator-0')).not.toHaveClass('carouselIndicator__active');
    expect(getByTestId('carousel-indicator-1')).toHaveClass('carouselIndicator__active');
  });
});