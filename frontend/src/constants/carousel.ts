import { SlideType } from '../components/carousel.types';

export const SLIDES: SlideType[] = [
  {
    bgColor: 'hsl(0deg 0% 6%)',
    textColor: '#fff',
    title: 'xPhone',
    text: 'Lots to love, Less to spend.\nStarting at $399.',
    img: 'iphone.png',
    imgSize: 'l',
  },
  {
    bgColor: 'hsl(0deg 0% 98%)',
    title: 'Tablet',
    text: 'Just the right amount of everything.',
    img: 'tablet.png',
    imgSize: 'm',
  },
  {
    bgColor: 'hsl(240deg 8% 95%)',
    title: 'Buy a Tablet or xPhone for college.\nGet arPods.',
    text: '',
    img: 'airpods.png',
    imgSize: 's',
  },
];

export const IMG_SIZE_MAP = {
  l: 'item-img-l',
  m: 'item-img-m',
  s: 'item-img-s',
};
