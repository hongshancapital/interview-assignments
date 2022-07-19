import { render } from "@testing-library/react";
import { renderHook, act } from '@testing-library/react-hooks';
import "@testing-library/jest-dom";
import React from "react";
import Carousel, { useAnimate } from '../index';
import type { CarouselItemProps } from '../index';

describe('测试useAnimate', () => {
  test('初始化', () => {
    const work = renderHook(() => useAnimate({count: 3, interval: 2000}))
    expect(work.result.current.activeIndex).toBe(0);
  });
  
  jest.useFakeTimers();
  test('执行多次时间间隔', () => {
    const work = renderHook(() => useAnimate({count: 3, interval: 2000}))
    act(() => {
      jest.advanceTimersByTime(2000);
    });
    expect(work.result.current.activeIndex).toBe(1);
    act(() => {
      jest.advanceTimersByTime(2000);
    });
    expect(work.result.current.activeIndex).toBe(2);
    act(() => {
      jest.advanceTimersByTime(2000);
    });
    expect(work.result.current.activeIndex).toBe(0);
  })
});

describe('测试Carousel组件', () => {
  const items: CarouselItemProps[] = [{
    titles: ['test1'],
  }, {
    titles: ['test2'],
  }, {
    titles: ['test3'],
  }];

  it('组件正常加载', () => {
    const { container } = render(<Carousel items={items} interval={2000} animationSpeed={500} />);
    const carousel = container.getElementsByClassName('carousel')[0];
    const itemsWrap = container.getElementsByClassName('carousel-content')[0];
    const dotsWrap = container.getElementsByClassName('carousel-dots')[0];
    expect(carousel).toBeDefined();
    expect(itemsWrap).toBeDefined();
    expect(dotsWrap).toBeDefined();
    expect(itemsWrap.children.length).toBe(3);
    expect(dotsWrap.children.length).toBe(3);
  });

  jest.useFakeTimers();
  test('执行多次时间间隔', () => {
    const { container } = render(<Carousel items={items} interval={2000} animationSpeed={500} />);
    const itemsWrap = container.getElementsByClassName('carousel-content')[0];
    expect(itemsWrap.getAttribute('class')).toContain('0');
    act(() => {
      jest.advanceTimersByTime(2000);
    })
    expect(itemsWrap.getAttribute('class')).toContain('1');
    act(() => {
      jest.advanceTimersByTime(2000);
    })
    expect(itemsWrap.getAttribute('class')).toContain('2');
    act(() => {
      jest.advanceTimersByTime(2000);
    })
    expect(itemsWrap.getAttribute('class')).toContain('0');
  });
});
