import React from 'react';
import { render } from '@testing-library/react';
import Card from './Card';
import Timeline from './Timeline';
import Carousel from '.';

const data = {
  titles: ['xPhone'],
  des: ['Lots to love. Less to spend.', 'Starting at $399.'],
  imgUrl:
    'https://www.google.com.hk/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png',
};

// 卡片测试
describe('Card test', () => {
  test('titles test', () => {
    const { getByText } = render(<Card data={data} />);
    data.titles.forEach((title) => {
      expect(getByText(title)).toBeInTheDocument();
    });
  });

  test('background img test', () => {
    const { container } = render(<Card data={data} />);
    const imgElements = container.querySelectorAll('.carousel-img');
    expect(imgElements.length).toEqual(1);
    expect(imgElements[0]).toBeInTheDocument();
  });
});

// 时间线测试
describe('Timeline test', () => {
  test('active timeline test', () => {
    const { container } = render(<Timeline isActive={true} duration={1} />);
    expect(container.querySelector('.active')).toBeInTheDocument();
  });

  test('inactive timeline test', () => {
    const { container } = render(<Timeline isActive={false} duration={1} />);
    expect(container.querySelector('.active')).toBeNull();
  });
});

// 幻灯片测试
describe('Carousel test', () => {
  test('active timeline', () => {
    const { container } = render(<Carousel data={[data]} />);
    expect(container.querySelector('.active')).toBeInTheDocument();
  });

  test('img count', () => {
    const { container } = render(<Carousel data={[data]} />);
    const imgs = container.querySelectorAll('.carousel-img');
    expect(imgs.length).toEqual(1);
  });
});
