import React, { useState, useEffect } from "react";
import Slide from "./SlideItem";
import { CarouselType } from "../types";

const Carousel = ({ 
  slides,
  delay,
  renderNavItem, 
  ...args
}: CarouselType) => {
  const [currentIndex, setCurrentIndex] = useState<number>(0);
  useEffect(() => {
    const timer = setTimeout(() => {
      setCurrentIndex((currentIndex + 1) % slides.length);
    }, delay);

    return () => clearTimeout(timer);
  }, [currentIndex, delay, slides]);
  
  return (
    <div className="slides" {...args}>
      <div className="slides-item" data-testid="slides-item" style={{
        width: `${slides.length * 100}%`, 
        transform: `translateX(${-100/slides.length*currentIndex}%)`
      }}>
        {slides.map(item => (
          <Slide
            key={item.id}
            id={item.id}
            title={item.title}
            text={item.text}
            description={item.description}
            img={item.img}
           />
        ))}
      </div>
      {renderNavItem && (
        <div className="slides-nav">
          {slides.map((item, index) => (
            renderNavItem(
              {
                isCurrent: currentIndex === index, 
                time: delay,
                key: `${currentIndex}-${item.id}`
              }
            )
          ))}
        </div>
      )}
    </div>
  );
}

export default Carousel;
