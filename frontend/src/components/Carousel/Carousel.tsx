import { FC, ReactNode } from 'react';
import styles from './Carousel.module.scss';
import { Indicators } from './Indicators';
import { useIndex } from './hooks';

type CarouselProps = {
  /**
   * Mille seconds for display each item
   * @default 3000
   */
  duration?: number;
  /**
   * Carousel items for display
   */
  children: ReactNode[];
};

/**
 * @Component Carousel
 * @description Can display passed in carousel items, will fit the width/height of the parent container
 */
export const Carousel: FC<CarouselProps> = ({ duration = 3000, children }) => {
  const carouselItemsLength = children.length;
  const activeIndex = useIndex({
    duration,
    childrenLength: carouselItemsLength,
  });

  return (
    <div className={styles.carousel}>
      <ul
        className={styles.carouselItems}
        style={{
          width: `${carouselItemsLength * 100}%`,
          transform: `translateX(calc(-1 * 100% / ${carouselItemsLength} * ${activeIndex}))`,
        }}
        aria-label="carousel items"
      >
        {children.map((carouselItem, index) => (
          <li
            className={styles.carouselItemWrapper}
            // NOTE: There is no insert/delete operation on carouselItems, so index is safe to use as key
            key={index}
            aria-label="carousel item"
            aria-hidden={index !== activeIndex}
          >
            {carouselItem}
          </li>
        ))}
      </ul>
      <Indicators
        activeIndex={activeIndex}
        length={carouselItemsLength}
        duration={duration}
      />
    </div>
  );
};
