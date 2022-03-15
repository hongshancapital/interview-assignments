import React, { useEffect, useState } from "react";

import Indicator from "./components/Indicator";
import SlideComponent from "./components/Slide";

import { Slide } from "../../types";

import "./index.css";

interface CarouselProps {
  slideList: Slide[];
  showIndicator?: boolean;
  delay?: number;
  defaultIndex?: number;
}

function Carousel(props: CarouselProps) {
  const { slideList, showIndicator = true, delay = 3000, defaultIndex = 2 } = props;
  const [currentIndex, setCurrentIndex] = useState<number>(defaultIndex);

  const slideLength = slideList.length;

  useEffect(() => {
    const timer = setTimeout(() => {
      // 如果当前已经是最后一张，返回最开始一张
      setCurrentIndex(currentIndex + 1 === slideLength ? 0 : currentIndex + 1);
    }, delay);
    return () => clearTimeout(timer);
  }, [currentIndex, delay, slideLength]);

  return (
    <div className="carousel-wrapper">
      <div
        className="carousel-content"
        style={{
          transform: `translateX(${(currentIndex / slideLength) * 100 * -1}%)`,
          width: `${100 * slideLength}%`,
        }}
      >
        {
          slideList.map(slide => <SlideComponent key={slide.id} {...slide} />)
        }
      </div>
      {
        showIndicator ? <Indicator currentIndex={currentIndex} delay={delay} length={slideLength} onClick={setCurrentIndex} /> : null
      }
    </div>
  )
}

export default Carousel
