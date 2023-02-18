import React from "react";
import { CarouselItemType } from "../../types";

import styles from "./index.module.css";

interface CarouselItemProps {
  item: CarouselItemType;
}

const CarouselItem: React.FC<CarouselItemProps> = ({
  item: {
    name = "",
    slogan = "",
    cost = "",
    backgroundColor = "",
    productImage,
  },
}) => {
  return (
    <div
      className={styles.item}
      style={{
        width: window.innerWidth,
        backgroundColor: backgroundColor,
      }}
    >
      <div className={styles.productInfo}>
        <h1 className={styles.title}>{name}</h1>
        <h2 className={styles.slogan}>{slogan}</h2>
        <h2 className={styles.cost}>{cost}</h2>
      </div>
      <div className={styles.productImage}>
        <img src={productImage} alt="" />
      </div>
    </div>
  );
};

export default CarouselItem;
