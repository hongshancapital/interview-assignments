import React from 'react';
import Carousel from '../Carousel';
import Item, { IItemProps } from "./item";

import Airpods from '../../assets/airpods.png';
import Iphone from '../../assets/iphone.png';
import Tablet from '../../assets/tablet.png';

const items: (IItemProps & { key: string })[] = [
  {
    key:'xPhone',
    title: 'xPhone',
    subTitle: 'Lots to love. Less to spend.\n Starting at $399.',
    img: Iphone,
    className: 'xPhone'
  },
  {
    key: 'tablet',
    title: 'Tablet',
    subTitle: 'Just the right amount of everything.',
    img: Tablet,
    className: 'tablet',
  },
  {
    key: 'airPods',
    title: 'Buy a tablet or xPhone for college.\n Get airPods',
    subTitle: '',
    img: Airpods,
    className: 'airPods'
  }
];

export default function DemoCarousel() {
  
  return (
    <Carousel duration={400}>
      {items.map(item => <Item {...item} />)}
    </Carousel>
  )
}
