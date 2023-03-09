import React, { useState, useRef, useEffect } from "react";
import CarouselItem, { ICarouselItem } from '../CarouselItem';
import './index.scss';

interface CarouselProps {
  items: ICarouselItem[];
  duration: number;      // 每一页停留时间
  speed: number;         // 翻页的滚动速度
}

const Carousel: React.FC<CarouselProps> = ({ items = [], duration, speed }) => {
  const [currentIndex, setCurrentIndex] = useState(0);
  const timerRef = useRef<number | null>(null);

  const handleNextTabClick = (): void => {
    const nextIndex = (currentIndex + 1) % items?.length;
    setCurrentIndex(nextIndex);
  }

  const handleTabClick = (index: number): void => {
    setCurrentIndex(index);
  }

  useEffect(() => {
    timerRef.current = setInterval(handleNextTabClick, duration);
    return () => {
      if (timerRef.current) {
        clearInterval(timerRef.current);
      }
    }
  }, [currentIndex]);

  return <div className="carousel">
    <div className="carousel-items"
      style={{
        width: 100 * items?.length + 'vw',
        transform: `translateX(-${100 * currentIndex}vw)`,
        transition: `all ${speed}ms`
      }}
    >
      {
        items?.map((item, index) => (
          <CarouselItem
            key={index}
            item={item}
          />
        ))
      }
    </div>
    <div className="carousel-tabs">
      {
        items?.map((item, index) => (
          <div
            key={index}
            className='carousel-tab'
            onClick={() => handleTabClick(index)}
          >
            <div
              className={`carousel-tab-progress ${index === currentIndex ? "active" : ""}`}
              style={{ animationDuration: `${duration}ms` }}
            />
          </div>
        ))
      }
    </div>
  </div>
}

export default Carousel;