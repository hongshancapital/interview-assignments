import React from 'react';

import { Carousel } from '@/components/carousel';

import IconAirpods from './assets/airpods.png';
import IconIphone from './assets/iphone.png';
import IconTablet from './assets/tablet.png';

const items: Array<any> = [
  {
    key: 0,
    title: 'xPhone',
    desc: 'Lots to love. Less to spend. \nStarting at $399.',
    style: {
      backgroundColor: 'rgb(17, 17, 17)',
      backgroundImage: `url(${IconIphone})`,
      color: '#fff',
    },
  },
  {
    key: 1,
    title: 'Tablet',
    desc: 'Just the right amount of everything.',
    style: {
      backgroundColor: 'rgb(248, 248, 248)',
      backgroundImage: `url(${IconTablet})`,
      color: '#000',
    },
  },
  {
    key: 2,
    title: 'Buy a tablet or xPhone for college. \nGet arpods.',
    desc: '',
    style: {
      backgroundColor: 'rgb(241, 241, 243)',
      backgroundImage: `url(${IconAirpods})`,
    },
  },
  {
    key: 3,
    title: '测试',
    desc: '',
    style: {
      backgroundColor: 'red',
    },
  },
];
const Item = (props: any) => {
  const { item } = props;
  return (
    <div className="demo-container" style={item.style}>
      <div className="demo-title">{item.title}</div>
      <div className="demo-desc">{item.desc}</div>
    </div>
  );
};
export default function CarouselDemo() {
  return (
    <Carousel>
      {items.map((item, i) => (
        <Item key={i} item={item} />
      ))}
    </Carousel>
  );
}
