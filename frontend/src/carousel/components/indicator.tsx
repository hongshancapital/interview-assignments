import React, { FC } from "react";
import styles from "../styles.module.scss";
import { ButtonProps, CarouselConfig } from "../type";

export const Indicator: FC<
  ButtonProps & Pick<CarouselConfig, "delay" | "autoplay">
  > = ({
         onMouseDown,
         slideIndex,
         activeIndex,
         delay,
         autoplay,
         onMouseUp,
         forceRefresh,
       }) => (
  <span
    onMouseDown={() => onMouseDown(slideIndex)}
    onMouseUp={() => onMouseUp(slideIndex)}
    style={{ animationDuration: `${delay}ms` }}
    className={`${styles.indicator} ${
      activeIndex === slideIndex && autoplay && !forceRefresh
        ? styles.indicatorInProgress
        : ""
    } ${activeIndex === slideIndex && !autoplay ? styles.indicatorActive : ""}`}
  />
);

