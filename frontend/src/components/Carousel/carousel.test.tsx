import React from 'react';
import { render, act, RenderResult } from '@testing-library/react';
import Carousel, { IProps } from './index';

const testEmptyProps = {};
const testSpeedProps = {
  speed: 3,
};
const testDotsProps = {
  dots: false,
};
const generateCarousel = (config: IProps) => {
  return (
    <Carousel {...config}>
      <div className="demo bg-1">
        <h3 className="title color-white">xPhone</h3>
        <p className="sub color-white">Lots to love. Less to spend</p>
        <p className="sub color-white">Starting at $399.</p>
      </div>
      <div className="demo bg-2">
        <h3 className="title color-black">Tablet</h3>
        <p className="sub color-black">Just the right amount of everything.</p>
      </div>
      <section className="demo bg-3">
        <h3 className="title color-black">
          Buy a Tablet or xPhone for college
        </h3>
        <h3 className="title color-black">Get arpods.</h3>
      </section>
      {null}
      {false}
    </Carousel>
  );
};

let wrapper: RenderResult,
  carouselElement: HTMLElement,
  carouselTrack: HTMLElement,
  dotsElement: HTMLElement;

describe('test carousel component by default props', () => {
  beforeEach(() => {
    wrapper = render(generateCarousel(testEmptyProps));
    carouselElement = wrapper.getByTestId('test-carousel');
    carouselTrack = carouselElement.querySelector(
      '.carousel-track'
    ) as HTMLElement;
    dotsElement = carouselElement.querySelector(
      '.carousel-dots'
    ) as HTMLElement;
    jest.useFakeTimers();
  });
  afterEach(() => {
    jest.useRealTimers();
  });

  it('should render correct carousel based on default props', () => {
    expect(carouselElement).toBeInTheDocument();
    expect(carouselTrack).toBeInTheDocument();
    expect(carouselTrack.querySelectorAll('.carousel-slide').length).toEqual(3);
    expect(dotsElement).toBeInTheDocument();
    expect(dotsElement.querySelectorAll('.carousel-dot').length).toEqual(3);
  });
});

describe('test carousel component by dots props', () => {
  beforeEach(() => {
    wrapper = render(generateCarousel(testDotsProps));
    carouselElement = wrapper.getByTestId('test-carousel');
    carouselTrack = carouselElement.querySelector(
      '.carousel-track'
    ) as HTMLElement;
    dotsElement = carouselElement.querySelector(
      '.carousel-dots'
    ) as HTMLElement;
  });

  it('should render correct carousel without dots props', () => {
    expect(carouselElement).toBeInTheDocument();
    expect(carouselTrack).toBeInTheDocument();
    expect(carouselTrack.querySelectorAll('.carousel-slide').length).toEqual(3);
    expect(dotsElement).toBeNull();
  });
});

describe('test timer', () => {
  beforeEach(() => {
    jest.useFakeTimers();
  });
  afterEach(() => {
    jest.useRealTimers();
  });
  it('should render correct at init time', () => {
    act(() => {
      wrapper = render(generateCarousel(testSpeedProps));
      carouselElement = wrapper.getByTestId('test-carousel');
      carouselTrack = carouselElement.querySelector(
        '.carousel-track'
      ) as HTMLElement;
      dotsElement = carouselElement.querySelector(
        '.carousel-dots'
      ) as HTMLElement;

      expect(carouselTrack).toContainHTML(
        'style="transition: transform 1.2s ease-in; transform: translateX(000%);"'
      );
      jest.advanceTimersByTime(3000);
      expect(carouselTrack).toContainHTML(
        'style="transition: transform 1.2s ease-in; transform: translateX(-100%);"'
      );
      expect(dotsElement.querySelectorAll('.carousel-dot')[1]).toContainHTML(
        '<i class="carousel-fill"'
      );
      jest.advanceTimersByTime(4000);
      expect(carouselTrack).toContainHTML(
        'style="transition: transform 1.2s ease-in; transform: translateX(-200%);"'
      );
      expect(dotsElement.querySelectorAll('.carousel-dot')[2]).toContainHTML(
        '<i class="carousel-fill"'
      );
    });
  });
});
