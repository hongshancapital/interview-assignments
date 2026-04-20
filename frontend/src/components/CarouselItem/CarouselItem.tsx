import { FC } from 'react';
import styles from './CarouselItem.module.scss';

export type CarouselItemProps = {
  imgSrc: string;
  title: string;
  subTitle?: string;
  className?: string;
};

/**
 * @Component CarouselItem
 * @description Render a carousel item based on passed in props
 */
export const CarouselItem: FC<CarouselItemProps> = ({
  imgSrc,
  title,
  subTitle = '',
  className = '',
}) => {
  return (
    <div className={`${styles.carouselItem} ${className}`}>
      <h2 className={styles.carouselItemTitle}>{title}</h2>
      <h3 className={styles.carouselItemSubTitle}>{subTitle}</h3>
      <img className={styles.carouselItemImage} src={imgSrc} alt={title} />
    </div>
  );
};
