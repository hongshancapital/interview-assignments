import { useState, useEffect } from 'react';
import CarouselCaption from './CarouselCaption';
import CarouselItem from './CarouselItem';

const Carousel = ({ children }) => {
  const [activeIndex, setActiveIndex] = useState(0)
  const numberOfChildren = children.length

  useEffect(() => {
      const timer = window.setInterval(
        () => {
          setActiveIndex((activeIndex + 1) % numberOfChildren);
        },
        3000
      );
      return () => clearInterval(timer);
    },
    [activeIndex, numberOfChildren]
  );
  
  const divStyle = {
    display: 'flex',
    alignItems: 'justify-content-center;'
  };

  return (
    <div style={divStyle}>
      { children }
    </div>
  );
};

Carousel.Item = CarouselItem;
Carousel.Caption = CarouselCaption;
export default Carousel;