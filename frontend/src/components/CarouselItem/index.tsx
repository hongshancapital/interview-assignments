import React from 'react';
import { CarouselItemInfo } from '../../types';

import './styles.scss';

interface CarouselItemProps extends CarouselItemInfo {}

const Carousel = (props: CarouselItemProps) => {
  const { title, description, background } = props;

  return (
    <div className={`carousel-item ${background}-background`}>
      <h1 className="carousel-item-title">{title}</h1>
      <p className="carousel-item-description">{description}</p>
    </div>
  );
}

export default Carousel;
