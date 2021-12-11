import React from 'react';
import { act, fireEvent, render, within } from '@testing-library/react';

import Carousel from '../Carousel';

/** 当前展示的轮播图选项 */
const ACTIVE_ITEM_SELECTOR = '.carousel-item.active';

/** 获取当前展示的轮播图选项 */
const getActiveItem = (container: HTMLElement): HTMLElement =>
  container.querySelector(ACTIVE_ITEM_SELECTOR) as HTMLElement;

beforeEach(() => {
  jest.useFakeTimers();
});

it('renders', () => {
  const { asFragment } = render(
    <Carousel
      options={[
        { title: 'title1', content: 'content1' },
        { title: 'title2', content: 'content2' },
      ]}
      style={{ backgroundColor: 'cyan' }}
    />,
  );
  expect(asFragment()).toMatchSnapshot();
});

it('render two options', () => {
  const { container } = render(
    <Carousel
      options={[
        { title: 'title1', content: 'content1', className: 'ele1' },
        { title: 'title2', className: 'ele2' },
      ]}
    />,
  );

  const firstItem = container.querySelector('.ele1') as HTMLElement;
  expect(firstItem).toBeTruthy();
  within(firstItem).getByText('title1');
  within(firstItem).getByText('content1');

  const secondItem = container.querySelector('.ele2') as HTMLElement;
  expect(secondItem).toBeTruthy();
  within(secondItem).getByText('title2');
});

it('auto render next item', () => {
  const { container } = render(
    <Carousel
      options={[
        { title: 'ele1', className: 'ele1' },
        { title: 'ele2', className: 'ele2' },
        { title: 'ele3', className: 'ele3' },
      ]}
      delay={1000}
    />,
  );
  act(() => {
    jest.advanceTimersByTime(1100);
  });
  let activeItem = getActiveItem(container);
  expect(activeItem).toBeTruthy();
  within(activeItem!).getByText('ele2');

  act(() => {
    jest.advanceTimersByTime(1100);
  });
  activeItem = getActiveItem(container);
  expect(activeItem).toBeTruthy();
  within(activeItem!).getByText('ele3');

  act(() => {
    jest.advanceTimersByTime(1100);
  });
  activeItem = getActiveItem(container);
  expect(activeItem).toBeTruthy();
  within(activeItem!).getByText('ele1');
});

it('use nav bar switch to next/prev item', () => {
  const { getByText, container } = render(
    <Carousel
      options={[
        { title: 'ele1', className: 'ele1' },
        { title: 'ele2', className: 'ele2' },
      ]}
      delay={1000}
    />,
  );
  const nextBtn = getByText('>');
  const prevBtn = getByText('<');

  fireEvent.click(nextBtn);
  let activeItem = getActiveItem(container);
  expect(activeItem).toBeTruthy();
  within(activeItem!).getByText('ele2');

  fireEvent.click(nextBtn);
  activeItem = getActiveItem(container);
  expect(activeItem).toBeTruthy();
  within(activeItem!).getByText('ele1');

  act(() => {
    jest.advanceTimersByTime(800);
  });
  fireEvent.click(prevBtn);
  activeItem = getActiveItem(container);
  expect(activeItem).toBeTruthy();
  within(activeItem!).getByText('ele2');

  // should reset timer
  act(() => {
    jest.advanceTimersByTime(1000);
  });
  activeItem = getActiveItem(container);
  expect(activeItem).toBeTruthy();
  within(activeItem!).getByText('ele1');
});

it('use indicator switch item', () => {
  const { container } = render(
    <Carousel
      options={[
        { title: 'ele1', className: 'ele1' },
        { title: 'ele2', className: 'ele2' },
        { title: 'ele3', className: 'ele3' },
      ]}
    />,
  );
  const indicator = container.querySelector('.carousel-indicator') as HTMLDivElement;
  expect(indicator).toBeTruthy();
  expect(indicator!.children.length).toStrictEqual(3);

  fireEvent.click(indicator!.children.item(1)!);
  let activeItem = getActiveItem(container);
  expect(activeItem).toBeTruthy();
  within(activeItem!).getByText('ele2');

  fireEvent.click(indicator!.children.item(0)!);
  activeItem = getActiveItem(container);
  expect(activeItem).toBeTruthy();
  within(activeItem!).getByText('ele1');
});
