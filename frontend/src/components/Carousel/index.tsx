import { useState, useRef, useEffect } from "react";
import classNames from "classnames";
import CarouselItem, { ICarouselItemProps } from '../CarouselItem';

import './index.scss';

interface CarouselProps {
  items: ICarouselItemProps[];
  // 每一页停留时间
  duration?: number;
  // 翻页的滚动速度
  speed?: number;
}

const Carousel: React.FC<CarouselProps> = ({ items = [], duration = 3000, speed = 300 }) => {
  const [currentIndex, setCurrentIndex] = useState(0);
  const timerRef = useRef<NodeJS.Timeout | null>(null);

  const handleTabClick = (index: number): void => {
    setCurrentIndex(index);
  }

  useEffect(() => {
    timerRef.current = setTimeout(() => {
      const nextIndex = (currentIndex + 1) % items.length;
      setCurrentIndex(nextIndex);
    }, duration);

    return () => {
      if (timerRef.current) {
        clearTimeout(timerRef.current);
      }
    }
  }, [currentIndex]);

  return <div className="carousel">
    <div className="carousel-items"
      style={{
        width: `${100 * items.length}vw`,
        transform: `translateX(-${100 * currentIndex}vw)`,
        transition: `all ${speed}ms`
      }}
    >
      {
        items.map((item, index) => (
          <CarouselItem
            key={index}
            {...item}
          />
        ))
      }
    </div>
    <div className="carousel-tabs">
      {
        items.map((_, index) => (
          <div
            key={index}
            className="carousel-tab"
            onClick={() => handleTabClick(index)}
          >
            <div
              className={classNames('carousel-tab-progress', index === currentIndex && 'active')}
              style={{ animationDuration: `${duration}ms` }}
            />
          </div>
        ))
      }
    </div>
  </div>
}

export default Carousel;