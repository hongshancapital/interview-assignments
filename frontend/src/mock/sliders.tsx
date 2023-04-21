import { Slider } from '../components/Carousel/types';
import airpods from '../assets/airpods.png';
import iphone from '../assets/iphone.png';
import tablet from '../assets/tablet.png';

export const sliders: Slider[] = [
  {
    id: 1,
    image: iphone,
    fontColor: '#ffffff',
    title: 'xPhone',
    subTitle: 'Lots to love. Less to spend.\nStarting at $399.',
  },
  {
    id: 2,
    image: tablet,
    fontColor: '',
    title: 'Tablet',
    subTitle: 'Just the right amount of everything.',
  },
  {
    id: 3,
    image: airpods,
    fontColor: '#000000',
    title: 'Buy a Tablet or Phone for college.\nGet arPods.',
  },
];

export const slidersEmpty: Slider[] = [];

export const slidersWithTooManyData: Slider[] = [
  {
    id: 1,
    image: iphone,
    fontColor: '#ffffff',
    title: 'xPhone',
    subTitle: 'Lots to love. Less to spend.\nStarting at $399.',
  },
  {
    id: 2,
    image: tablet,
    fontColor: '',
    title: 'Tablet',
    subTitle: 'Just the right amount of everything.',
  },
  {
    id: 3,
    image: airpods,
    fontColor: '#000000',
    title: 'Buy a Tablet or Phone for college.\nGet arPods.',
  },
  {
    id: 4,
    image: iphone,
    fontColor: '#ffffff',
    title: 'xPhone',
    subTitle: 'Lots to love. Less to spend.\nStarting at $399.',
  },
  {
    id: 5,
    image: airpods,
    fontColor: '#000000',
    title: 'Buy a Tablet or Phone for college.\nGet arPods.',
  },
];
