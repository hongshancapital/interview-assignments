import { ICarouselItem } from '../interface/carousel';
import iphoneImage from '../assets/CarouselImages/iphone.png';
import tabletImage from '../assets/CarouselImages/tablet.png';
import airpodsImage from '../assets/CarouselImages/airpods.png';

export const CAROUSEL_LIST: ICarouselItem[] = [
  {
    backgroundColor: '#000',
    color: '#fff',
    image: 'iphone',
    title: 'xPhone',
    text: ['Lots to love. Less to spend.', 'Starting at $399.'],
  },
  {
    image: 'tablet',
    title: 'Tablet',
    text: 'Just the right amount of everything.',
  },
  {
    backgroundColor: '#eee',
    image: 'airpods',
    title: 'Buy a Tablet or xPhone for college.',
    subtitle: 'Get airPods.',
    text: '',
  },
];

export const IMAGES_MAPPER = {
  iphone: iphoneImage,
  tablet: tabletImage,
  airpods: airpodsImage,
};
