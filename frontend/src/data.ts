import { ICarouselItem } from './components/Carousel';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import airpods from './assets/airpods.png';

export const data: ICarouselItem[] = [
  {
    img: iphone,
    titles: ['xPhone'],
    descs: ['Lots to love. Less to spend.', 'Starting at $399.'],
    wrapperStyle: {
      color: '#FFFFFF',
      backgroundColor: '#0F0F0F',
    },
  },
  {
    img: tablet,
    titles: ['Tablet'],
    descs: ['Just the right amount of everything.'],
    wrapperStyle: {
      color: '#000000',
      backgroundColor: '#FAFAFA',
    },
  },
  {
    img: airpods,
    titles: ['Buy a Tablet or xPhone for college.', 'Get arPods.'],
    wrapperStyle: {
      color: '#000000',
      backgroundColor: '#F2F2F4',
    },
  },
];
