import { FC } from 'react';

import Carousel from './carousel';
import { CarouselItem } from './carousel-item';
import { Indicator } from './indicator';
import {
  CarouselProps,
  CarouselItemProps,
  CarouselIndicatorProps,
} from './interface';

export type {
  CarouselProps,
  CarouselItemProps,
  CarouselIndicatorProps,
} from './interface';

export type IComponentCarousel = FC<CarouselProps> & {
  Item: FC<CarouselItemProps>;
  Indicator: FC<CarouselIndicatorProps>;
};

const ComponentCarousel = Carousel as IComponentCarousel;

ComponentCarousel.Item = CarouselItem;
ComponentCarousel.Indicator = Indicator;

export default ComponentCarousel;
