import React from 'react';
import { render } from '@testing-library/react';
import { CarouselInfo } from './interface/carousel';
import Carousel from './components/carousel/index';
import { act } from 'react-dom/test-utils';

const testList: CarouselInfo[] = [
  {
    title: ['titleTest1'],
    subTitle: ['subTest1'],
  },
  {
    title: ['test2'],
    subTitle: ['subTest2'],
  },
  {
    title: ['test3'],
    subTitle: ['subTest3'],
  },
];

const template = <Carousel duration={1000} carouselList={testList} />;

const globalTimeout = global.setTimeout;
export const sleep = async (timeout = 1500) => {
  await act(async () => {
    await new Promise((resolve) => {
      globalTimeout(resolve, timeout);
    });
  });
};

test('renders carouselItem', async () => {
  const { container, getByText } = render(template);
  const linkElement = getByText(/titleTest1/i);
  expect(linkElement).toBeInTheDocument();
  await sleep();
  expect(container.querySelectorAll('.carousel-list')[0]).toHaveStyle(
    'transform: translateX(-100%);'
  );
  await sleep(1000);
  expect(container.querySelectorAll('.carousel-list')[0]).toHaveStyle(
    'transform: translateX(-200%);'
  );
});

test('renders progress', async () => {
  const { container } = render(template);

  expect(
    container.querySelectorAll('.progress-wrap .progress')[0].children[0]
  ).toHaveClass('progress-active');
  await sleep(1500);
  expect(
    container.querySelectorAll('.progress-wrap .progress')[1].children[0]
  ).toHaveClass('progress-active');
});
