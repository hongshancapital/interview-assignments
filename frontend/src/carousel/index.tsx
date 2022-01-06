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
  const [forceRefresh, setForceRefresh] = useState(false);

  const changeActiveIndex = (index: number) => {
    if (index === data.length - 1) {
      setActiveIndex(0);
    } else {
      setActiveIndex(index + 1);
    }
    if (forceRefresh) {
      setForceRefresh(false);
    }
  };

  useInterval(
    () => {
      changeActiveIndex(activeIndex);
    },
    autoplay ? delay : null,
    activeIndex,
    forceRefresh
  );

  const onButtonClick = (index: number) => {
    setActiveIndex(index);
    if (index === activeIndex) {
      setForceRefresh(true);
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
            onClick={onButtonClick}
            // need to refresh the button animation when click the same button, I try to avoid change the DOM manually.
            // So the tricky way is to change the key of the button, so the React Diff Algorithm assumes this is a new element, and it will refresh the button.
            key={forceRefresh ? index + Math.random() : index}
            slideIndex={index}
          />
        ))}
      </div>
    </div>
  );
};
