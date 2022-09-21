import React from 'react';
import { act } from 'react-dom/test-utils';
import { render } from '@testing-library/react';
import Carousel from '../../components/Carousel';
import carouselData from '../../mockData/carousel';

const delay = 3000;

describe('测试 Carousel 组件', () => {

  beforeEach(() => {
    jest.useFakeTimers();
  })

  afterEach(() => {
    jest.useRealTimers();
  })

  test('renders product list', () => {
    const comp = render(<Carousel />);
    expect(comp).toBeDefined();
  });

  test('定时器工作正常', () => {
    const comp = render(<Carousel />);
    const containerW = carouselData.length * 100;
    const style = comp.container.querySelector('.banner')?.getAttribute('style');
    const span = comp.container.querySelectorAll('.progress-bar-item span')[0];
    expect(span).toHaveClass('active');
    expect(style).toEqual(`width: ${containerW}vw; transform: translateX(-0vw);`);
    act(() => {
      jest.advanceTimersByTime(delay);
    });
    const styleAfter = comp.container.querySelector('.banner')?.getAttribute('style');
    const spanAfter = comp.container.querySelectorAll('.progress-bar-item span')[1];
    expect(spanAfter).toHaveClass('active');
    expect(styleAfter).toEqual(`width: ${containerW}vw; transform: translateX(-100vw);`);
  })
  
})
