import React from 'react';
import { CarouselItemPropsType } from './interface';
import styles from './index.module.css';
/**
 * @description: 每个轮播子项的容器
 * @param {string} customBackgroundColor
 * @param {any} children 每个轮播图子项的可业务定制化内容
 * @author: Zhao Min 曌敏
 */
const CarouselItem = ({
  customBackgroundColor,
  children,
}: CarouselItemPropsType) => {
  return (
    <div
      className={styles.body}
      style={{
        backgroundColor: customBackgroundColor,
      }}
    >
      {children}
    </div>
  );
};

export default CarouselItem;
