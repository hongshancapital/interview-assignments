import React from 'react';
import { fireEvent, render } from '@testing-library/react';

import Carousel, { CarouselProps } from './index';

const defaultProps: CarouselProps = {
  style: { width: 600, height: 240 },
  indicator: <Carousel.Indicator />,
  autoplay: false,
  onChange: jest.fn(),
};

describe('Carousel', () => {
  it('render default correctly', () => {
    const wrapper = render(
      <Carousel {...defaultProps}>
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

    const carouselElem = wrapper.container.querySelector('.pr-carousel');
    const wrapperElem = carouselElem?.querySelector('.pr-carousel-wrapper');
    const ctrlElem = carouselElem?.querySelector('.pr-carousel-ctrl');
    const ctrlLeftElem = carouselElem?.querySelector(
      '.pr-carousel-ctrl-left'
    ) as HTMLDivElement;
    const dotElem = carouselElem?.querySelector(
      '.pr-carousel-dot'
    ) as HTMLSpanElement;

    expect(carouselElem).toBeInTheDocument();
    expect(wrapperElem).toBeInTheDocument();
    expect(wrapperElem).toHaveClass('pr-carousel-fade');
    expect(wrapperElem?.querySelectorAll(':scope > li').length).toEqual(3);
    expect(wrapperElem?.firstChild).toHaveClass(
      'pr-carousel-item pr-carousel-item-active'
    );
    expect(ctrlElem).toBeInTheDocument();
    expect(ctrlLeftElem).toBeInTheDocument();
    fireEvent.click(ctrlLeftElem);
    expect(defaultProps.onChange).toHaveBeenCalled();
    expect(dotElem).toBeInTheDocument();
    fireEvent.click(dotElem?.firstChild as HTMLElement);
    expect(defaultProps.onChange).toHaveBeenCalled();
  });
});
