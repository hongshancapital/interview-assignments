import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import { Carousel } from '../carousel';

describe('test carousel', () => {
  it('carousel snapshot', () => {
    // 渲染
    const { container } = render(<Carousel />);
    // 快照记录
    expect(container).toMatchSnapshot();
  });

  it('carousel elements', () => {
    const { getByTestId } = render(<Carousel />);
    const swiperWrapper = getByTestId('swiper-wrapper');
    const indicatorWrapper = getByTestId('indicator-wrapper');
    expect(swiperWrapper).toBeInTheDocument();
    expect(indicatorWrapper).toBeInTheDocument();
  });

  it('carousel indicator click', () => {
    const { getByTestId } = render(<Carousel />);
    const indicatorWrapper = getByTestId('indicator-wrapper');
    const indicatorElementTwo = indicatorWrapper.lastElementChild;
    fireEvent.click(indicatorElementTwo!);
    expect(indicatorElementTwo!.firstChild).toHaveStyle({
      transition: `3000ms ease-in-out`,
    });
  });
});