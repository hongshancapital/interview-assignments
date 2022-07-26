import { ICarouselItem } from '../components/carousel-demo/type';

import iphonePng from '../assets/iphone.png';
import airPodsPng from '../assets/airpods.png';
import tabletPng from '../assets/tablet.png';

// 走马灯mock数据
export const carouselList: ICarouselItem[] = [
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
