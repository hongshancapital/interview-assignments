import React from 'react';
import { render, screen, waitFor, act, cleanup } from '@testing-library/react';
import { Carousel, CarouselItem, defineCarouselProps } from './Carousel';

describe('Carousel', () => {
  beforeEach(() => {
    jest.useFakeTimers('modern');
    jest.setTimeout(300000);
  });
  afterEach(() => {
    jest.useRealTimers();
    cleanup();
  });

  it('defaultProps', () => {
    const { container } = render(<Carousel />);
    expect(container).toContainHTML('style="width: 100%; height: 100%;"');
    expect(container).toContainHTML('style="transform: translate3d(000%, 000%, 0); transition-duration: 1000ms;"');
    expect(container).toContainHTML('class="cj-carousel-indicator"');
    expect(container).not.toContainHTML('class="cj-carousel-navi cj-carousel-navi__next"');
  });
  it('width & height', () => {
    const { container } = render(<Carousel width="100vw" height="100vh" />);
    expect(container).toContainHTML('style="width: 100vw; height: 100vh;"');
  });
  it('showIndicator & showNavi', () => {
    const { container } = render(<Carousel showIndicator showNavi />);
    expect(container).toContainHTML('style="width: 100%; height: 100%;"');
    expect(container).toContainHTML('style="transform: translate3d(000%, 000%, 0); transition-duration: 1000ms;"');
    expect(container).toContainHTML('class="cj-carousel-indicator"');
    expect(container).toContainHTML('class="cj-carousel-navi cj-carousel-navi__next"');
  });
  it('animation slideLeft slideRight', async () => {
    const { container, rerender } = render(<Carousel animation={'slideLeft'} />);
    expect(container).toContainHTML('dir="ltr"');
    rerender(<Carousel animation="slideRight" />);
    expect(container).toContainHTML('dir="rtl"');
  });

  it('with CarouselItem', () => {
    const { container } = render(
      <Carousel>
        <CarouselItem>slide1</CarouselItem>
        <CarouselItem>slide2</CarouselItem>
        <CarouselItem>slide3</CarouselItem>
      </Carousel>
    );
    expect(container).toContainHTML('slide1');
    expect(container).toContainHTML('slide2');
    expect(container).toContainHTML('slide3');
  });
  it('defineCarouselProps', () => {
    const props = { width: '100vw', height: '100vh', showNavi: false, showIndicator: true, overPause: true };
    expect(defineCarouselProps(props)).toStrictEqual(props);
  });
});
