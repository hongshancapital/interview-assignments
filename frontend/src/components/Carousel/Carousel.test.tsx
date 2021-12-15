import React from 'react';
import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';

import Carousel from './index';
import { CarouselConfig } from '../../config';

describe('Carousel', () => {
  test('测试轮播图自动播放', () => {
    // jest.useFakeTimers();
    const fn = jest.fn();

    const wrapper = render(<Carousel config={CarouselConfig} auto delay={3000} onChange={fn} />);
    const el = wrapper.baseElement;
    const box = el.querySelector('.carousel-view-box');
    const rect = el.querySelector('.carousel-view-flag');

    // jest.runAllTimers();

    expect(box.children.length).toBe(3); // 渲染图片数量
    expect(rect.children.length).toBe(3); // 渲染轮播图指示数量
    expect(fn).toBeCalled(); // onChange事件触发

    expect(wrapper.asFragment()).toMatchSnapshot();

    // jest.useRealTimers(); // 使用realTimer
   });

   test('测试轮播图手动播放', () => {
    const fn = jest.fn();

    const wrapper = render(<Carousel config={CarouselConfig} auto={false} delay={3000} onChange={fn} />);
    const el = wrapper.baseElement;
    const rect = el.querySelector('.carousel-view-flag');

    userEvent.click(rect.children[0]);
    expect(rect.children[0]).toHaveClass('active'); // 点击第一个图片, 此时的active状态也应该在第一张图片上

    userEvent.click(rect.children[2]);
    expect(rect.children[2]).toHaveClass('active'); // 点击第三个图片, 此时的active状态也应该在第三张图片上

    expect(fn).toBeCalled(); // onChange事件触发
   });
});

