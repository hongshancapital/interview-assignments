import { FC } from 'react';
import { APP_CAROUSEL_ITEMS, DEFAULT_CAROUSEL_DURATION } from './constants';
import { CarouselItem, CarouselItemProps } from './CarouselItem';
import { Indicators } from './Indicators';
import styles from './Carousel.module.scss';
import { useIndex } from './hooks';

type CarouselProps = {
  /**
   * Time to display each item
   * @default 3000ms
   */
  duration?: number;
  carouselItems?: CarouselItemProps[];
};

/**
 * Carousel can display items for given duration(default to 3000ms)
 * only support fullscreen slide for now
 */
export const Carousel: FC<CarouselProps> = ({
  duration = DEFAULT_CAROUSEL_DURATION,
  carouselItems = APP_CAROUSEL_ITEMS,
}) => {
  const carouselItemsLength = carouselItems.length;
  const { currIndex } = useIndex({
    duration,
    childrenLength: carouselItemsLength,
  });

  return (
    <div className={styles.carousel}>
      <ul
        className={styles.carouselItems}
        style={{
          width: `${carouselItemsLength * 100}vw`,
          transform: `translateX(-${currIndex * 100}vw)`,
        }}
        aria-label="carousel items"
      >
        {carouselItems.map((carouselItemProps) => {
          return (
            <li
              className={styles.carouselItemWrapper}
              key={carouselItemProps.title}
            >
              <CarouselItem {...carouselItemProps} />
            </li>
          );
        })}
      </ul>
      <Indicators
        length={carouselItemsLength}
        currentActiveIndex={currIndex}
        duration={duration}
      />
    </div>
  );
};
