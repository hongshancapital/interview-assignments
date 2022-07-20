import React from 'react';
import { render } from '@testing-library/react';
import { unmountComponentAtNode } from 'react-dom';
import { act } from "react-dom/test-utils";
import Carousel from './index';  

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

it('Carousel render', () => {

  // act 插入一个 banner
  // expeact 无切换按钮

  // act 插入三个 banner
  // expeact 三个切换按钮
  // expeact 选中第一个切换按钮

  // act 定时器执行3秒
  // expeact 3秒后滚动到第二屏，选中第二个切换按钮

  // act 定时器执行3秒
  // expeact 3秒后滚动到第三屏，选中第三个切换按钮

  // act 定时器执行3秒
  // expeact 3秒后滚动到第一屏，选中第一个切换按钮

});