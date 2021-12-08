import React from 'react';
import { render } from '@testing-library/react';
import fireEvent from '@testing-library/user-event';
import { MyCarouselItem, IMyCarouselItem } from './index';

describe('Test MyCarouselItem', () => {
  const item: IMyCarouselItem = {
    title: 'TITLE',
    subTitle: ['SUBTITLE1', 'SUBTITLE2'],
    image: 'http://image.path.com',
    style: {
      backgroundColor: 'black',
      color: 'white',
    },
  };
  // 判断组件元素是否渲染成功
  test('check render', () => {
    const { container } = render(<MyCarouselItem {...item} />);
    const containers = container.querySelectorAll('.my_carousel_item');
    expect(containers.length).toBe(1);

    const images = container.querySelectorAll('.my_carousel_item__image');
    expect(images.length).toBe(1);

    const titles = container.querySelectorAll('.my_carousel_item__title');
    expect(titles.length).toBe(1);

    const subtitles = container.querySelectorAll('.my_carousel_item__subtitle');
    expect(subtitles.length).toBe(2);
  });

  test('check style', () => {
    const { container } = render(<MyCarouselItem {...item} />);
    const [itemContainer] = container.querySelectorAll('.my_carousel_item');
    expect(itemContainer.style.backgroundColor).toBe('black');
    expect(itemContainer.style.color).toBe('white');
  });

  test('check click', () => {
    const onClick = jest.fn();
    const { container } = render(
      <MyCarouselItem {...item} onClick={onClick} />
    );
    const [itemContainer] = container.querySelectorAll('.my_carousel_item');
    fireEvent.click(itemContainer);
    expect(onClick.mock.calls.length).toBe(1);
  });
});
