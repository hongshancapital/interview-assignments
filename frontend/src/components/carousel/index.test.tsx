import React from 'react';
import { render } from '@testing-library/react';
import Carousel from './index';

test('渲染轮播的子项', () => {
  const { getByText } = render(<Carousel><div>I am a carousel children</div></Carousel>);
  const carouselItem = getByText(/I am a carousel children/i);
  expect(carouselItem).toBeInTheDocument();
});

test('渲染轮播的指示器', () => {
  const { getAllByRole } = render(<Carousel><div>I am a carousel children</div><div>I am a carousel children</div></Carousel>);
  const indicatorItems = getAllByRole("link");
  expect(indicatorItems).toHaveLength(2)
});


test('渲染轮播的子项的个数应该是正确的', () => {
  const { getAllByText } = render(<Carousel><div>I am a carousel children</div><div>I am a carousel children</div></Carousel>);
  const carouselItems = getAllByText(/I am a carousel children/i);
  expect(carouselItems).toHaveLength(2)
});

test('渲染的轮播高度应该根据传入的height来确定', () => {
  const height = 160;
  const { getByRole } = render(<Carousel height={height}><div>I am a carousel children</div></Carousel>);
  const sliders = getByRole("sliders")
  expect(sliders).toHaveStyle({ height: `${height}px` })
});


test('轮播的defaultActiveIndex应该激活对应的卡片', () => {
  const defaultActiveIndex = 2;
  const childCount = 5;
  const arr = new Array(childCount);
  arr.fill(0);
  const { getByRole } = render(
    <Carousel defaultActiveIndex={defaultActiveIndex}>
      {
        arr.map((_, index) => <div key={index}>I am a carousel children</div>)
      }
    </Carousel>);
  const sliders = getByRole("sliders")
  expect(sliders).toHaveStyle({ transform: `translateX(${ -100 * (defaultActiveIndex % childCount) }%)` })
});
