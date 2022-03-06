import React, { FC, CSSProperties } from "react";

import styles from "./index.module.css";

interface CarouselItemProps {
  style?: CSSProperties;
}

const CarouselItem: FC<CarouselItemProps> = (props) => {
  const { children, style } = props;
  return (
    <div style={style} className={styles.carouselItem}>
      {children}
    </div>
  );
};

export default CarouselItem;
