import React from 'react';
import { render, unmountComponentAtNode } from 'react-dom';
import { act } from 'react-dom/test-utils';
import { Carousel } from '../../Carousel/Carousel';

const bannerListData: any[] = [
  {
    id: 0,
    title: 'xPhone',
    desc: 'Lots to love. Less to spend.\nStarting at $399.',
    img: require('../../../assets/iphone.png'),
    style: { color: 'white' },
  },
  {
    id: 1,
    title: 'Tablet',
    desc: 'Just the right amount of everything.',
    img: require('../../../assets/tablet.png'),
  },
  {
    id: 2,
    title: 'Buy a tablet or xPhone for college.\nGet airpods.',
    img: require('../../../assets/airpods.png'),
  },
];

let container: any = null;
beforeEach(() => {
  // 创建一个 DOM 元素作为渲染目标
  container = document.createElement('div');
  document.body.appendChild(container);
  jest.useFakeTimers();
});

afterEach(() => {
  // 退出时进行清理
  unmountComponentAtNode(container);
  container.remove();
  container = null;
  jest.useRealTimers();
});

it('轮播应该渲染完整', () => {
  act(() => {
    render(<Carousel delaySeconds={3} itemList={bannerListData} />, container);
  });
  const items = container.querySelectorAll('.carousel_item');
  expect(items.length).toEqual(3);
});

it('轮播应该开始自动播放', () => {
  const onChange = jest.fn();
  act(() => {
    render(
      <Carousel
        delaySeconds={2}
        itemList={bannerListData}
        onChange={onChange}
      />,
      container
    );
  });
  act(() => {
    jest.advanceTimersByTime(5000);
  });
  expect(onChange).toBeCalled();
});
