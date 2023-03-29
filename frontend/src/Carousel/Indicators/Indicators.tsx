import { FC } from 'react';
import styles from './Indicators.module.scss';

type IndicatorsProps = {
  currentActiveIndex: number;
  length: number;
  duration: number;
};

export const Indicators: FC<IndicatorsProps> = ({
  currentActiveIndex,
  length,
  duration,
}) => {
  const indicators = new Array(length).fill(null);
  return (
    <div className={styles.indicators}>
      {indicators.map((_, index) => {
        const isActive = currentActiveIndex === index;
        return (
          <div
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
            ></span>
          </div>
        );
      })}
    </div>
  );
};
