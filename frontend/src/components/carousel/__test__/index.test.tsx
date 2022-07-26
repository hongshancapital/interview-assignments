import React from 'react';
import { act, render } from '@testing-library/react';
import Carousel from '../Carousel';

const Component = () => {
  return <Carousel>
    <div>test1</div>
    <div>test2</div>
    <div>test3</div>
  </Carousel>
}

beforeEach(() => {
  jest.useFakeTimers();
});

afterEach(() => { 
  jest.useRealTimers();
})

test('Carousel should be created', () => {
  const { container } = render(<Component />);
  const carouselElement = container.querySelectorAll('.carousel__container');
  expect(carouselElement.length).toBe(1);
  const carouselItemList = container.querySelectorAll('.carousel__item');
  expect(carouselItemList.length).toBe(3);
  const indictorList = container.querySelectorAll('.carousel-indicator');
  expect(indictorList.length).toBe(3);
})

test('Carousel should auto play', () => {
  
  const { container } = render(<Component />);

  const wrapper = container.querySelector('.carousel__wrap') as HTMLDivElement;
  expect(wrapper?.style.width).toBe('300%');
  expect(wrapper?.style.transform.includes('translateX')).toBeTruthy();

  const indicatorElement = container.querySelector('.carousel-indicator__progress');
  const indictorList = container.querySelectorAll('.carousel-indicator');
  expect(indicatorElement?.parentElement).toBe(indictorList[0]);
  
  act(() => { 
    jest.advanceTimersByTime(3000);
  });
  const secondIndicatorElement = container.querySelector('.carousel-indicator__progress');
  expect(secondIndicatorElement?.parentElement).toBe(indictorList[1]);
  expect(wrapper?.style.transform.includes('translateX(-33')).toBeTruthy();

  act(() => {
    jest.advanceTimersByTime(3000);
  });

  const thirdIndicatorElement = container.querySelector('.carousel-indicator__progress');
  expect(thirdIndicatorElement?.parentElement).toBe(indictorList[2]);
  expect(wrapper?.style.transform.includes('translateX(-66')).toBeTruthy();


  act(() => {
    jest.advanceTimersByTime(3000);
  });

  const firstIndicatorElement = container.querySelector('.carousel-indicator__progress');
  expect(firstIndicatorElement?.parentElement).toBe(indictorList[0]);
})