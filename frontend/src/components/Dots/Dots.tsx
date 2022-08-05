import React, { FC, memo, useMemo } from "react";
import { CarouselDotsProps } from '../../types/carousel';
import './Dots.css';

const CarouselDots: FC<CarouselDotsProps> = memo((dotsProps) => {
    const { count, onSlideToIndex, currentStateIndex, delay } = dotsProps;

    const dots = useMemo(() => {
      return new Array(count).fill(0);
    }, [count]);

    if (!count) {
      return null;
    }

    return  <ul className="carousel-dots">
      {dots.map((_, index) => <li
          key={`dotKey${index}`}
          className="carousel-dots-item"
          onClick={() => onSlideToIndex(index)}
        >
        {currentStateIndex === index && (
          <span
            className="carousel-dots-highlight"
            style={{
              animationDuration: `${delay}ms`,
            }}
          />
        )}</li>)}
    </ul>
})

export default CarouselDots;