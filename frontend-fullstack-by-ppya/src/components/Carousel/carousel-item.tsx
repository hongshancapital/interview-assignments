import React, { FC } from 'react';
import styles from './index.module.less';

export interface CarouselItemPropType {
  title?: string | React.ReactNode;
  subTitle?: string | React.ReactNode;
  imgUrl: string,
}

const CarouselItem: FC<CarouselItemPropType> = (props) => {
  const { title, subTitle, imgUrl } = props;

  return (
    <div className={styles['carousel-item']}>
      <div className={styles['carousel-item-title']}>{title}</div>
      <div className={styles['carousel-item-subtitle']}>{subTitle}</div>
      < img src={imgUrl} alt="" className={styles['carousel-item-img']} />
    </div>
  );
}

export default CarouselItem;