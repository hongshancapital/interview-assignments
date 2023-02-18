import React, { useEffect, useState } from "react";

import styles from "./index.module.css";
import { CarouselItemType } from "./types";
import CarouselItem from "./_libs/CarouselItem";
import CarouseLoading from "./_libs/CarouseLoading";

interface CarouselProps {
  items?: CarouselItemType[];
  duration?: number;
}

const Carousel: React.FC<CarouselProps> = ({ items = [], duration = 4 }) => {
  const [activeIndex, setActiveIndex] = useState(0);
  const [innerWidth, setInnerWidth] = useState(window.innerWidth);

  useEffect(() => {
    const interval = setInterval(() => {
      if (activeIndex < items.length - 1) {
        setActiveIndex((active) => active + 1);
      } else {
        setActiveIndex(0);
      }
    }, duration * 1000);
    return () => {
      clearInterval(interval);
    };
  }, [activeIndex, items.length, duration]);

  useEffect(() => {
    const handleResize = () => {
      setInnerWidth(window.innerWidth);
    };
    window.addEventListener("resize", handleResize);

    return () => {
      window.removeEventListener("resize", handleResize);
    };
  }, []);

  if (!items.length) {
    return null;
  }

  const slideTranslateX = `translateX(-${activeIndex * 100}vw)`;

  return (
    <div className={styles.container}>
      <div
        className={styles.container_slide}
        style={{
          width: items.length * innerWidth,
          transform: slideTranslateX,
        }}
      >
        {items.map((item, index) => {
          return <CarouselItem key={index} item={item} />;
        })}
      </div>
      <div className={styles.loading}>
        <CarouseLoading current={activeIndex} duration={duration} />
      </div>
    </div>
  );
};

export default Carousel;
