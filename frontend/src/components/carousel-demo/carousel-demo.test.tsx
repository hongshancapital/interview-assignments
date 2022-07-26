import React from 'react';
import { render } from '@testing-library/react';
import { CarouselDemo } from '.';
import { ICarouselItem } from './type';

import iphonePng from '../../assets/iphone.png';
import airPodsPng from '../../assets/airpods.png';
import tabletPng from '../../assets/tablet.png';

const items: ICarouselItem[] = [
  {
    id: 0,
    titles: ['xPhone'],
    contents: ['Lots to love. Less to spend', 'Starting at $399.'],
    image: iphonePng,
    titleColor: '#fff',
    contentColor: '#fff',
    bgColor: '#101010',
  },
  {
    id: 1,
    titles: ['Tablet'],
    contents: ['Just the right amount of everything'],
    image: tabletPng,
    titleColor: '#000',
    contentColor: '#010100',
    bgColor: '#fafafa',
  },
  {
    id: 2,
    titles: ['Buy a Tablet or xPhone for college', 'Get arPods'],
    contents: [],
    image: airPodsPng,
    titleColor: '#010101',
    bgColor: '#f2f2f3',
  },
];

describe('Unit Test from CarouselDemo component', () => {
  it('should CarouseDemo render correctly', () => {
    const { getByText } = render(<CarouselDemo items={items} />);

    // render title correctly
    expect(getByText('xPhone')).toBeInTheDocument();

    // render content corrent
    expect(getByText('Buy a Tablet or xPhone for college')).toBeInTheDocument();
  });
})
