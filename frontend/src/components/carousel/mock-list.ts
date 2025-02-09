import type { CarouselItemProps } from 'components/carousel';
import IPhone from 'assets/iphone.png';
import Tablet from 'assets/tablet.png';
import Airpods from 'assets/airpods.png';

const list: CarouselItemProps[] = [
  {
    id: 1,
    img: IPhone,
    title: 'xPhone',
    description: 'Lots to Love. Less to spend. \n Starting at $399.',
    carouselItemProps: {
      style: {
        color: '#fff',
        backgroundColor: '#111',
      },
    },
  },
  {
    id: 2,
    img: Tablet,
    title: 'Tablet',
    description: 'Just the right amount of everything.',
    carouselItemProps: {
      style: {
        color: '#000',
        backgroundColor: '#fafafa',
      },
    },
  },
  {
    id: 3,
    img: Airpods,
    title: 'Buy a Tablet or xPhone for college. \n Get airPods',
    carouselItemProps: {
      style: {
        color: '#000',
        backgroundColor: '#f1f1f3',
      },
    },
  },
];

export default list;
