import React from 'react';
// import { render as domRender, unmountComponentAtNode } from "react-dom";
import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import '@testing-library/jest-dom';
import { testTranslate } from './__tests__/common'
import App from './App';
import { CarouselConfig } from './const'

// 基础测试
describe('1: BasicTest', () => {
  test('1.1: renderTest（节点符合预期）', done => {
    const { container } = render(<App/>);
    const {children: items} = container.querySelector('.carousel-container .carousel-content') || {};
    if (CarouselConfig.length === 0) {
      expect(items?.length).toBe(0);
      done();
      return
    }
    expect(items?.length).toBe(CarouselConfig.length + 2);
    expect(items?.[0].innerHTML).toBe(items?.[items?.length - 2].innerHTML);
    expect(items?.[1].innerHTML).toBe(items?.[items?.length - 1].innerHTML);
    done();
  });

  test('1.2: UITest（配置样式符合预期）', done => {
    const { container } = render(<App/>);
    const {children: items} = container.querySelector('.carousel-container .carousel-content') || {};
    CarouselConfig.forEach((item, i) => {
      const child = items?.[i + 1];
      const inner = child?.innerHTML;
      item.title.length && expect(inner).toContain(item.title[0]);
      (item as {subTitle: string[]})?.subTitle?.length && expect(inner).toContain((item as {subTitle: string[]}).subTitle[0]);
      const container = child?.querySelector('.banner-container');
      expect(container).toBeDefined();
    });
    done();
  });

  jest.useFakeTimers();
  jest.spyOn(global, 'setTimeout');
  test('1.3: watingTimeTest（interval 时间符合预期）', () => {
    const { container } = render(<App/>);
    const content = container.querySelector('.carousel-container .carousel-content');
    expect(content).toBeDefined();
    testTranslate(content, 1);
    expect(setTimeout).toHaveBeenCalledTimes(2);
    expect(setTimeout).toHaveBeenNthCalledWith(1, expect.any(Function), 2500); // waiting
    expect(setTimeout).toHaveBeenLastCalledWith(expect.any(Function), 50); // dot step
  });
});

// 基础事件测试
describe('2: EventTest', () => {
  test('2.1: clickDotTest（点击 dot 跳转）', () => {
    const { container } = render(<App/>);
    const content = container.querySelector('.carousel-container .carousel-content');
    const dots = container.querySelector('.carousel-dots');
    userEvent.click(dots?.children[1] as Element);
    testTranslate(content, 2);
  });
});

