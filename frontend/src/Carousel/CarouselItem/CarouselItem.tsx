import { FC } from 'react';
import styles from './CarouselItem.module.scss';

export type CarouselItemProps = {
  imgSrc: string;
  title: string;
  subTitle?: string;
  className?: string;
};

export const CarouselItem: FC<CarouselItemProps> = ({
  imgSrc,
  title,
  subTitle = '',
  className = '',
}) => {
  return (
    <div
      className={`${styles.carouselItem} ${className}`}
      aria-label="carousel item"
    >
      <h2 className={styles.carouselItemTitle}>{title}</h2>
      <h3 className={styles.carouselItemSubTitle}>{subTitle}</h3>
      <img className={styles.carouselItemImage} src={imgSrc} alt={title} />
    </div>
  );
};
