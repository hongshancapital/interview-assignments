import React, { FC } from "react";
import styles from "../styles.module.scss";
import { ButtonProps, CarouselConfig } from "../type";

export const Indicator: FC<
  ButtonProps & Pick<CarouselConfig, "delay" | "autoplay">
  > = ({ onClick, slideIndex, activeIndex, delay, autoplay }) => (
  <span
    onClick={() => onClick(slideIndex)}
    style={{ animationDuration: `${delay}ms` }}
    className={`${styles.indicator} ${
      activeIndex === slideIndex && autoplay ? styles.indicatorInProgress : ""
    } ${activeIndex === slideIndex && !autoplay ? styles.indicatorActive : ""}`}
  />
);
