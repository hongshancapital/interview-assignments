import React from "react";
import './CarouselItem.css';

interface Props {
  children: React.ReactNode;
}

const CarouselItem: React.FC<Props> = (props) => {
  return <div className="carousel-item">
    { props.children }
  </div>;
}

export default CarouselItem;
