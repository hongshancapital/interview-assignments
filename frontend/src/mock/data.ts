import { CarouselItemProps } from '../components/index';
import iphone from '../assets/iphone.png';
import tablet from '../assets/tablet.png';
import airpods from '../assets/airpods.png';

export const data: CarouselItemProps[] = [
  {
    id: 1,
    img: iphone,
    title: 'xPhone',
    desc: 'Lots to love. Less to spend. Starting at $399.',
    color: '#FFFFFF',
    bgColor: '#111111',
  },
  {
    id: 2,
    img: tablet,
    title: 'Tablet',
    desc: 'Just the right amount of everything.',
    bgColor: '#FAFAFA',
  },
  {
    id: 3,
    img: airpods,
    title: 'Buy a Tablet or xPhone for college. Get arPods.',
    desc: '',
    bgColor: '#F1F1F3',
  },
];
