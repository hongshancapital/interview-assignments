import React from 'react';
import { render, cleanup, waitFor, fireEvent } from '@testing-library/react';
import { Carousel } from '.';
import { useCarousel } from './use-carousel';
import { renderHook } from '@testing-library/react-hooks';

describe('Unit Test for Carousel component', () => {
  afterEach(cleanup);
  it('should render Carousel component', () => {
    const { getByText } = render(<Carousel><p>Default</p></Carousel>);
    expect(getByText('Default')).toBeInTheDocument();
  });

  it('should transition after switch', async () => {
    const { container } = render(<Carousel>
      <span>1</span>
      <span>2</span>
      <span>3</span>
    </Carousel>);

    // 统计卡片帧数
    const count =container.getElementsByClassName('carousel-step-item').length;
    expect(count).toBe(3);

    // 测试点击切换
    fireEvent.click(container.getElementsByClassName('carousel-step-content')[1]);
    const items = await waitFor(() => container.getElementsByClassName('carousel-step-item'));
    expect(items[1]).toHaveClass('carousel-step-item-active');
  });

  it('should valid index', async () => {
    const { result, waitForNextUpdate } = renderHook(() => useCarousel({ defaultActiveIndex: 0, duration: 300, count: 3 }));

    // 默认位置
    expect(result.current.curIndex).toBe(0);

    // 300ms 后切换
    await waitForNextUpdate();
    expect(result.current.curIndex).toBe(1);
  })
});
