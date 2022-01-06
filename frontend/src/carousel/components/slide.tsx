import { CarouselConfig, SlideProps } from "../type";
import React, { FC } from "react";
import styles from "../styles.module.scss";

export const Slide: FC<SlideProps & Pick<CarouselConfig, "delay">> = ({
  title,
  description,
  image,
  textColor,
  bgColor,
  imageScaleRate = 1,
  activeIndex,
  slideIndex,
}) => {
  return (
    <div
      className={`${styles.slide} ${styles["slideBgScale" + imageScaleRate]}`}
      style={{
        backgroundColor: bgColor,
        backgroundImage: `url(${image})`,
        transform: `translateX(${(slideIndex - activeIndex) * 100}%)`,
      }}
    >
      <div className={styles.title}>
        {title.map((t, index) => (
          <h2 style={{ color: textColor }} key={index}>
            {t}
          </h2>
        ))}
      </div>

      <div style={{ color: textColor }}>
        {description?.map((d, index) => (
          <p className={`${styles.desc}`} key={index}>
            {d}
          </p>
        ))}
      </div>
    </div>
  );
};
