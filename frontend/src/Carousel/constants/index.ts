import { CarouselItemProps } from '../CarouselItem';
import styles from '../CarouselItem/CarouselItem.module.scss';
import airPodImg from '../../assets/airpods.png';
import iphoneImg from '../../assets/iphone.png';
import tabletImg from '../../assets/tablet.png';

export const APP_CAROUSEL_ITEMS: CarouselItemProps[] = [
  {
    imgSrc: iphoneImg,
    title: 'xPhone',
    subTitle: `Lots to love.Less to spend.\nStarting at $399.`,
    className: styles.carouselItemIphone,
  },
  {
    imgSrc: tabletImg,
    title: 'Tablet',
    subTitle: `Just the right amount of everything.`,
    className: styles.carouselItemTablet,
  },
  {
    imgSrc: airPodImg,
    title: 'Buy a Tablet or xPhone for college.\nGet airPods.',
    className: styles.carouselItemAirPod,
  },
];

export const DEFAULT_CAROUSEL_DURATION = 3000;
