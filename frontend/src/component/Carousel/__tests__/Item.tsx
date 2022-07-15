import React from 'react';
import { Item } from '../Item';
import { render, unmountComponentAtNode } from 'react-dom';
import { act } from 'react-dom/test-utils';

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

it('渲染出单个item项', () => {
  act(() => {
    render(
      <Item
        id="118"
        title="测试项目"
        img={require('../../../assets/iphone.png')}
      />,
      container
    );
  });
  expect(container.textContent).toContain('测试项目');
});
