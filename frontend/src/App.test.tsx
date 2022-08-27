import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders learn react link', () => {
  const { getByTestId } = render(<App />);
  let duration = 3000;
  //轮播容器
  var carouselContainer = getByTestId('carousel-container');
  //已经在DOM中
  expect(carouselContainer).toBeInTheDocument();
  //轮播时长每次3000ms
  expect(carouselContainer.style).toContain(duration+'ms');
  //当前应该是第一个
  expect(carouselContainer.style).toContain('translateX(-0%)');
  //当前轮播条
  var step1 = getByTestId("iphone");
  expect(step1).toBeInTheDocument();

  //3秒之后再判断
  setTimeout(function () {
    carouselContainer = getByTestId('carousel-container');
    //当前应该是第2个
    expect(carouselContainer.style).toContain('translateX(-100%)');
  }, duration)
  
});
