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

  /**
   * 正常流程，点击指示器，滑动到对应的slide
   * 点击当前slide的指示器，slide的倒计时重置，此时需要刷新指示器的动画
   * 而指示器的动画依赖forceRefresh的状态，此时需要通过重设forceRefresh的状态来刷新指示器的动画
   * 因此在mousedown的时候，需要设置forceRefresh为true，然后在mouseup的时候，设置forceRefresh为false
   */
  const onMouseDown = (index: number) => {
    setActiveIndex(index);
    if (index === activeIndex) {
      setForceRefresh(true);
    }
  };

  const onMouseUp = (index: number) => {
    if (index === activeIndex) {
      setForceRefresh(false);
    }
  }
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
            onMouseDown={onMouseDown}
            onMouseUp={onMouseUp}
            key={index}
            forceRefresh={forceRefresh}
            slideIndex={index}
          />
        ))}
      </div>
    </div>
  );
};
