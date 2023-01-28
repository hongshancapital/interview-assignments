import { ReactNode } from 'react';

import './Carousel.css';

interface ICarouselProps {
  children: ReactNode;
}

function CarouselItem(props: ICarouselProps) {
  return <div className="carousel-item">{props.children}</div>;
}

export default CarouselItem;
