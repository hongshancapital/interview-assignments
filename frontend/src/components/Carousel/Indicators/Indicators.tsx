import { FC } from 'react';
import styles from './Indicators.module.scss';

type IndicatorsProps = {
  activeIndex: number;
  length: number;
  duration: number;
};

/**
 * @Component Indicators
 * @description Carousel indicators, which are progressbar at the bottom of the carousel.
 */
export const Indicators: FC<IndicatorsProps> = ({
  activeIndex,
  length,
  duration,
}) => {
  const indicators = new Array(length).fill(null);
  return (
    <ul className={styles.indicators}>
      {indicators.map((_, index) => {
        const isActive = activeIndex === index;
        return (
          <li
            className={styles.indicator}
            aria-label="indicator"
            role="progressbar"
            // There is no insert/delete/resort case for indicators
            // so use index as key is fine.
            key={index}
          >
            <span
              className={styles.indicatorProgress}
              aria-label="indicator progress"
              style={{
                opacity: isActive ? 1 : 0,
                animation: isActive
                  ? `${styles.progress} ${duration}ms linear`
                  : 'none',
              }}
            />
          </li>
        );
      })}
    </ul>
  );
};
