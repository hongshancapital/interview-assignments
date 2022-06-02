import React from 'react';
import airpods from '../assets/airpods.png';
import iphone from '../assets/iphone.png';
import tablet from '../assets/tablet.png';

export interface ICarouselItem {
  title: string;
  subTitle: string;
  backgroundColor: string;
  fontColor: string;
  imageStyle: React.CSSProperties;
}

export const CarouselList: Array<ICarouselItem> = [
  {
    title: 'xPhone',
    subTitle: 'Lots to love, Less to spend. Starting as $399.',
    backgroundColor: '#111',
    fontColor: '#fff',
    imageStyle: {
      backgroundImage: `url(${iphone})`,
      backgroundSize: '50%',
      backgroundPosition: '50% 90%',
    },
  },
  {
    title: 'Tablet',
    subTitle: 'Just the right amount of everything.',
    backgroundColor: '#f9f9f9',
    fontColor: '#111',
    imageStyle: {
      backgroundImage: `url(${tablet})`,
      backgroundSize: '90%',
      backgroundPosition: '50% 90%',
    },
  },
  {
    title: 'Buy a Tablet or xPhone for college.\nGet airPods',
    subTitle: '',
    backgroundColor: '#f1f1f1',
    fontColor: '#111',
    imageStyle: {
      backgroundImage: `url(${airpods})`,
      backgroundSize: '120%',
      backgroundPosition: '50% 90%',
    },
  },
];
