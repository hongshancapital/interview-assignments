import React from 'react';
import { render, act } from '@testing-library/react';
import CarouselDemo from './demo'

describe('CarouselDemo test', () => {
  beforeEach(() => {
    jest.setTimeout(300000);
    jest.useFakeTimers('modern');
  });
  afterEach(() => {
    jest.useRealTimers();
  });
  it('calls the callback after 1 second via advanceTimersByTime', async () => {
    // 使用act包装组件测试代码，可以模拟真实浏览器形为，组件的生命周期都会被执行到
    // @see https://reactjs.org/docs/test-utils.html#act
    act(() => {
      const { container } = render(<CarouselDemo />);
      // 初始渲染
      expect(container).toContainHTML('translate3d(000%, 000%, 0);');
      // 快进4秒 第4秒
      jest.advanceTimersByTime(4000);
      expect(container).toContainHTML('translate3d(-100%, 000%, 0);');
      // 快进3秒 第7秒
      jest.advanceTimersByTime(3000);
      expect(container).toContainHTML('translate3d(-200%, 000%, 0);');
      // 快进3秒 第10秒
      jest.advanceTimersByTime(3000);
      expect(container).toContainHTML('translate3d(000%, 000%, 0);');
      // 快进3秒 第13秒
      jest.advanceTimersByTime(3000);
      expect(container).toContainHTML('translate3d(-100%, 000%, 0);');
    });
  });
});
