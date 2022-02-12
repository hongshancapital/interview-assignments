import { ICarouselConfigType } from '../components/Carousel/type';

import iphonePng from '../assets/iphone.png';
import tabletPng from '../assets/tablet.png';
import airpodsPng from '../assets/airpods.png';

export const CarouselConfig: ICarouselConfigType[] = [
  {
    id: 1,
    img: iphonePng,
    title: 'xPhone',
    subTitle: ['Lots to love. Less to spend.', 'Starting at $399.'],
    color: '#ffffff',
    backgroundColor: '#111111',
    imgWidth: '70%',
  },
  {
    id: 2,
    img: tabletPng,
    title: 'Tablet',
    subTitle: 'Just the right amount of everything.',
    color: '#000000',
    backgroundColor: '#FAFAFA',
    imgWidth: '130%',
  },
  {
    id: 3,
    img: airpodsPng,
    title: ['Buy a Tablet or xPhone for college.', 'Get arPods.'],
    color: '#000000',
    backgroundColor: '#F1F1F3',
    imgWidth: '180%',
  }
];
