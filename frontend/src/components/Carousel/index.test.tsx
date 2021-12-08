import React from 'react';
import { render } from '@testing-library/react';
import fireEvent from '@testing-library/user-event';
import Carousel, { ICarouselItem } from './index';

describe('Test Carousel', () => {
  const items: ICarouselItem[] = [
    {
      url: 'http://image.path.com',
      title: 'TEXT1',
      style: {
        backgroundColor: 'black',
        color: 'white',
      },
    },
    {
      url: 'http://image.path.com',
      title: 'TEXT2',
      style: {
        backgroundColor: 'black',
        color: 'white',
      },
    },
  ];
  // 判断组件元素是否渲染成功
  test('check render', () => {
    const { container } = render(<Carousel items={items} />);
    const containers = container.querySelectorAll('.carousel_panel');
    expect(containers.length).toBe(1);

    const itemsPanel = container.querySelectorAll('.carousel_panel__items');
    expect(itemsPanel.length).toBe(1);
    const [panel] = itemsPanel;
    // expect(panel.style.transform).toBe(
    //   `translateX(-${(currIndex * 100) / items.length}%)`
    // );
    expect(panel.style.width).toBe(`${items.length}00%`);

    const indicators = container.querySelectorAll('.carousel_panel__indicator');
    expect(indicators.length).toBe(1);
  });

  // 测试点击轮播项
  test('check click', () => {
    const onClick = jest.fn();
    const { container } = render(<Carousel items={items} onClick={onClick} />);
    const [itemContainer] = container.querySelectorAll('.carousel_item');
    fireEvent.click(itemContainer);
    expect(onClick.mock.calls.length).toBe(1);
  });

  // 测试点击指示器
  test('check indicator', () => {
    const { container } = render(<Carousel items={items} />);
    const [itemContainer] = container.querySelectorAll(
      '.carousel_panel__items'
    );
    expect(itemContainer.style.transform).toBe(`translateX(-0%)`);

    const [firstIndicator, secondIndicator] = container.querySelectorAll(
      '.carousel_indicator'
    );
    fireEvent.click(secondIndicator);
    expect(itemContainer.style.transform).toBe(`translateX(-50%)`);

    fireEvent.click(firstIndicator);
    expect(itemContainer.style.transform).toBe(`translateX(-0%)`);
  });

  // 自动滚动与时间间隔
  test('check auto scroll', async () => {
    const { container } = render(
      <Carousel items={items} auto interval={1000} />
    );
    const [itemContainer] = container.querySelectorAll(
      '.carousel_panel__items'
    );
    expect(itemContainer.style.transform).toBe(`translateX(-0%)`);
    // use waitFor need update @testing-library/react to ^@11
    setTimeout(() => {
      expect(itemContainer.style.transform).toBe(`translateX(-50%)`);
    }, 1000);
  });
});
