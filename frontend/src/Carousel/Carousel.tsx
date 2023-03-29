import { FC, ReactNode } from 'react';
import { APP_CAROUSEL_ITEMS, DEFAULT_CAROUSEL_DURATION } from './constants';
import { Indicators } from './Indicators';
import styles from './Carousel.module.scss';
import { useIndex } from './hooks';
import { CarouselItem } from './CarouselItem';

type CarouselProps = {
  /**
   * Time to display each item
   * @default 3000ms
   */
  duration?: number;
  /**
   * Carousel items to display
   * @default defaultCarouselItems
   */
  children?: ReactNode[];
};

const defaultCarouselItems: ReactNode[] = APP_CAROUSEL_ITEMS.map(
  (carouselItemProps) => (
    <CarouselItem {...carouselItemProps} key={carouselItemProps.title} />
  )
);

/**
 * Carousel can display items for given duration(default to 3000ms).
 * Will fit the container's with and height and hidden the overflow.
 */
export const Carousel: FC<CarouselProps> = ({
  duration = DEFAULT_CAROUSEL_DURATION,
  children = defaultCarouselItems,
}) => {
  const childrenLength = children.length;

  const { currIndex } = useIndex({
    duration,
    childrenLength,
  });

  return (
    <div className={styles.carousel}>
      <ul
        className={styles.carouselItems}
        style={{
          width: `${childrenLength * 100}%`,
          transform: `translateX(calc(-1 * 100% / ${childrenLength} * ${currIndex}))`,
        }}
        aria-label="carousel items"
      >
        {children.map((child, index) => {
          return (
            // Note: There is no insert/delete/resort case for children
            // so use index as key is fine.
            <li className={styles.carouselItemWrapper} key={index}>
              {child}
            </li>
          );
        })}
      </ul>
      <Indicators
        length={childrenLength}
        currentActiveIndex={currIndex}
        duration={duration}
      />
    </div>
  );
};
