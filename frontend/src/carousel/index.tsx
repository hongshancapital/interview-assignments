import { CarouselConfig, ICarouselProps } from "./type";
import React, { FC, useState } from "react";
import { Slide } from "./components/slide";
import styles from "./styles.module.scss";
import { Indicator } from "./components/indicator";
import { useInterval } from "./components/useInterval";

export const Carousel: FC<ICarouselProps & CarouselConfig> = ({
  data,
  width = 500,
  height = 600,
  delay = 3000,
  autoplay = true,
}) => {
  const [activeIndex, setActiveIndex] = useState(0);
  const [forceRefreshToken, setForceRefreshToken] = useState(0);

  const changeActiveIndex = (index: number) => {
    if (index === data.length - 1) {
      setActiveIndex(0);
    } else {
      setActiveIndex(index + 1);
    }
    if (forceRefreshToken > 0) {
      setForceRefreshToken(0);
    }
  };

  useInterval(
    () => {
      changeActiveIndex(activeIndex);
    },
    autoplay ? delay : null,
    activeIndex,
    forceRefreshToken
  );

  const onClick = (index: number) => {
    setActiveIndex(index);
    if (index === activeIndex) {
      setForceRefreshToken(forceRefreshToken + 1);
    }
  };

  return (
    <div style={{ width, height }} className={styles.container}>
      {data.map((item, index) => (
        <Slide
          {...item}
          delay={delay}
          slideIndex={index}
          activeIndex={activeIndex}
          key={index}
        />
      ))}

      <div className={styles.indicatorContainer}>
        {data.map((item, index) => (
          <Indicator
            delay={delay}
            autoplay={autoplay}
            activeIndex={activeIndex}
            onClick={onClick}
            key={index}
            slideIndex={index}
          />
        ))}
      </div>
    </div>
  );
};