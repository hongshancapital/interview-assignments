import React from 'react';
import { useClassName } from '../../hooks';
import { Aspect } from '../aspect';
import { useCarouselContext } from './CarouselContext';
import classes from './CarouselItem.module.css';

export type CarouselItemProps = {
  className?: string | undefined | null;
}

export const CarouselItem: React.FC<CarouselItemProps> = ({ className, children }) => {
  const { ratio } = useCarouselContext();
  return (
    <Aspect ratio={ratio}>
      <div className={useClassName(classes.item, 'carousel-item', className)}>
        {children}
      </div>
    </Aspect>
  );
};
