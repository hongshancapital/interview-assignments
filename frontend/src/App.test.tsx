import React from 'react';
import { render } from '@testing-library/react';
import { unmountComponentAtNode } from 'react-dom';
import { act } from "react-dom/test-utils";
import App from './App';  

let container: any = null;

beforeEach(() => {
  // 创建一个 DOM 元素作为渲染目标
  container = document.createElement('div');
  document.body.appendChild(container);
});

afterEach(() => {
  // 退出时进行清理
  unmountComponentAtNode(container);
  container.remove();
  container = null;
});

const resizeWindow = (x: number) => {
  window.innerWidth = x;
  window.dispatchEvent(new Event('resize'));
}

it('App render', () => {
  act(() => {
    render(<App />, container);
  });

  const dots = document.querySelectorAll('.carousel-dot');

  // 渲染三个容器
  expect(document.querySelectorAll('.carousel-panel').length).toBe(3);
  // 渲染三个按钮
  expect(dots.length).toBe(3);

  const track = document.querySelector('.carousel-panels') as HTMLElement;
  const panel = document.querySelector('.iphone') as HTMLElement;

  // 验证容器宽度是否与窗口宽度相等
  expect(parseInt(panel.style.width)).toBe(window.innerWidth);
  // 验证轨道宽度是否为窗口宽度 * 容器个数
  expect(parseInt(track.style.width)).toBe(window.innerWidth * 3);
  
  // 点击第三个按钮
  act(() => {
    dots[2].dispatchEvent(new MouseEvent('click', { bubbles: true }));
  });

  // 滚动到第三个容器
  expect(track.style.transform.includes(String(window.innerWidth * 2)));
  // 第三个按钮加载动画
  expect(dots[2].children[0].className).toBe('animation');

  // 改变窗口宽度
  act(() => {
    resizeWindow(800);
  })

  // 再次验证容器宽度是否与窗口宽度相等
  expect(parseInt(panel.style.width)).toBe(window.innerWidth);
  // 再次验证轨道宽度是否为窗口宽度 * 容器个数
  expect(parseInt(track.style.width)).toBe(window.innerWidth * 3);

  // todo: 模拟定时器
  // todo: 子组件测试
});