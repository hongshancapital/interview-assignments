export interface CarouselDotProps {
  isActive: boolean; 
  duration: number;
}

export interface CarouselItemProps {
  className?: string;
  titles?: string[];
  describes?: string[];
  titleClassName?: string;
  describeClassName?: string;
  img?: string;
}

export interface CarouselProps {
  items: CarouselItemProps[];
  interval?: number;
  animationSpeed?: number;
}

export interface useAnimateProps {
  count: number;
  interval: number;
}
