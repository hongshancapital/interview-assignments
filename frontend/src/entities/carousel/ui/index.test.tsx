import React from 'react';
import { act, render } from '@testing-library/react';
import type { CarouselItem } from '../index'
import { Carousel } from '../index';

import IphoneImg from 'assets/iphone.png'
import TabletImg from 'assets/tablet.png'
import AirpodsImg from 'assets/airpods.png'
import { useDot } from './model';

const List: CarouselItem[] = [
  {
      id: 1,
      img: IphoneImg,
      title: 'xPhone',
      description: 'Lots to Love. Less to spend. \n Starting at $399.',
      color: '#fff',
      backgroundColor: '#111'
  },
  {
      id: 2,
      img: TabletImg,
      title: 'Tablet',
      description: 'Just the right amount of everything.',
      color: '#000',
      backgroundColor: '#fafafa'
  },
  {
      id: 3,
      img: AirpodsImg,
      title: 'Buy a Tablet or xPhone for college. \n Get airPods',
      description: '',
      color: '#000',
      backgroundColor: '#f1f1f3'
  },
]

test('render CarouselItem Dot Count', () => {
  const { getAllByRole } = render(<Carousel list={List}></Carousel>);
  const renderItem = getAllByRole('button')

  expect(renderItem.length).toBe(3);
});

test('render Carousel Animation right', () => {
  const { getAllByRole } = render(<Carousel list={List}></Carousel>);
  const renderItem = getAllByRole('button')
  act(() => {
    renderItem[1].click()
  })

  expect(renderItem[1].parentElement).toHaveClass('dot-active');
});
