import React from 'react';
import { render } from '@testing-library/react';
import fireEvent from '@testing-library/user-event';
import { CarouselItem, ICarouselItem } from './item';

describe('Test CarouselItem', () => {
  const item: ICarouselItem = {
    title: 'TITLE',
    url: 'http://image.path.com/',
    style: {
      backgroundColor: 'black',
      color: 'white',
    },
  };
  // 判断组件元素是否渲染成功
  test('check render', () => {
    const { container } = render(<CarouselItem {...item} />);
    const containers = container.querySelectorAll('.carousel_item');
    expect(containers.length).toBe(1);

    const images = container.querySelectorAll('.carousel_item__image');
    expect(images.length).toBe(1);
    const [imageElement] = images;
    expect(imageElement.src).toBe(item.url);

    const titles = container.querySelectorAll('.carousel_item__title');
    expect(titles.length).toBe(1);
    const [titleElement] = titles;
    expect(titleElement.innerHTML).toBe(item.title);
  });

  test('check style', () => {
    const { container } = render(<CarouselItem {...item} />);
    const [containerElement] = container.querySelectorAll('.carousel_item');
    expect(containerElement.style.backgroundColor).toBe(
      item.style!.backgroundColor
    );
    expect(containerElement.style.color).toBe(item.style!.color);
  });

  test('check params', () => {
    const { container } = render(<CarouselItem {...item} />);
    const [imgElement] = container.querySelectorAll('.carousel_item__image');
    expect(imgElement.src).toBe(item.url);
    expect(imgElement.alt).toBe(item.title);
  });

  test('check click', () => {
    const onClick = jest.fn();
    const { container } = render(<CarouselItem {...item} onClick={onClick} />);
    const [itemContainer] = container.querySelectorAll('.carousel_item');
    fireEvent.click(itemContainer);
    expect(onClick.mock.calls.length).toBe(1);
  });
});
