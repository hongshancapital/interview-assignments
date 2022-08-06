import React from 'react';
import { fireEvent, render, RenderResult } from '@testing-library/react';
import { act } from 'react-dom/test-utils';

import Carousel, { CarouselProps } from './index';

const defaultProps: CarouselProps = {
  style: { width: 600, height: 240 },
  indicator: <Carousel.Indicator />,
  autoplay: false,
  onChange: jest.fn(),
};

const indicatorProps: CarouselProps = {
  style: { width: 600, height: 240 },
  indicator: <Carousel.Indicator />,
  autoplay: true,
  interval: 3000,
  onChange: jest.fn(),
};

let wrapper: RenderResult;

const genRenderCarousel = (props = defaultProps) => {
  return (
    <Carousel {...props}>
      <Carousel.Item>
        <img alt="item one" src="https://cdn.fujia.site/banner-1.jpg" />
      </Carousel.Item>
      <Carousel.Item>
        <img alt="item two" src="https://cdn.fujia.site/banner-2.jpg" />
      </Carousel.Item>
      <Carousel.Item>
        <img alt="item three" src="https://cdn.fujia.site/banner-4.jpg" />
      </Carousel.Item>
    </Carousel>
  );
};

describe('Carousel', () => {
  beforeEach(() => {
    jest.useFakeTimers();
  });
  afterEach(() => {
    jest.useRealTimers();
  });

  it('render default correctly', () => {
    wrapper = render(genRenderCarousel());

    const carouselElem = wrapper.container.querySelector('.pr-carousel');
    const wrapperElem = carouselElem?.querySelector('.pr-carousel-wrapper');
    const indicatorElem = carouselElem?.querySelector(
      '.pr-carousel-indicator'
    ) as HTMLSpanElement;

    expect(carouselElem).toBeInTheDocument();
    expect(wrapperElem).toBeInTheDocument();
    expect(wrapperElem).toHaveClass('pr-carousel-slide');
    expect(wrapperElem?.querySelectorAll(':scope > li').length).toEqual(3);
    expect(wrapperElem?.firstChild).toHaveClass(
      'pr-carousel-item pr-carousel-item-active'
    );

    expect(defaultProps.onChange).not.toHaveBeenCalled();
    expect(indicatorElem).toBeInTheDocument();
    expect(indicatorElem).toHaveClass(
      'pr-carousel-indicator-bottom pr-carousel-indicator-line'
    );
    fireEvent.click(indicatorElem?.firstChild as HTMLElement);
    expect(defaultProps.onChange).toHaveBeenCalled();
    fireEvent.click(indicatorElem?.lastChild as HTMLElement);
    expect(wrapperElem?.lastChild).toHaveClass('pr-carousel-item-active');
  });

  it('indicator', () => {
    act(() => {
      wrapper = render(genRenderCarousel(indicatorProps));
    });
    const carouselElem = wrapper.container.querySelector('.pr-carousel');
    const indicatorElem = carouselElem?.querySelector(
      '.pr-carousel-indicator-item-animate'
    ) as HTMLSpanElement;

    expect(indicatorElem).toBeInTheDocument();
    act(() => {
      jest.advanceTimersToNextTimer();
    });
    expect(indicatorProps.onChange).toHaveBeenCalled();
  });
});
