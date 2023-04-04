import { FC, MouseEventHandler, useCallback } from 'react';
import styles from './index.module.scss';

interface IndicatorProps {
  total: number;
  cur: number;
  onIndexSelected: (index: number) => void;
  onTimingEnd?: () => void;
}

const Indicator: FC<IndicatorProps> = ({
  total,
  cur,
  onIndexSelected,
  onTimingEnd,
}) => {
  const onDotsClick: MouseEventHandler = useCallback(
    (e) => {
      if (e.target === e.currentTarget) {
        return;
      }
      if (!(e.target instanceof HTMLElement)) {
        return;
      }
      const indexStr = e.target.dataset.index;
      if (indexStr === undefined) {
        return;
      }
      const index = parseInt(indexStr);
      if (index !== cur) {
        onIndexSelected(index);
      }
    },
    [cur, onIndexSelected],
  );

  return (
    <ul className={styles.dots} onClick={onDotsClick}>
      {new Array(total).fill(null).map((item, index) => {
        const isCur = cur === index;
        return (
          <li
            key={index}
            data-index={index}
            className={styles.dot + ' ' + (isCur ? styles.cur : '')}
            onAnimationEnd={isCur ? onTimingEnd : undefined}
          />
        );
      })}
    </ul>
  );
};

export default Indicator;
