import { useState, useEffect, useMemo, Children, memo } from 'react';
import type { PropsType } from './index.d';
import Item from './Item';
import styles from './index.module.css';

const Carousel: React.FC<PropsType> = ({
  className,
  style = null,
  showIndicator = true,
  autoplay = false,
  duration = 3000,
  speed = 500,
  timingFunction = 'ease',
  children,
}) => {
  // combine classes
  const classNames = useMemo(() => className ? `${className} ${styles.carousel}` : styles.carousel, [className]);
  // current visible item index, default 0
  const [currentIndex, setCurrentIndex] = useState<number>(0);
  // children array
  const childrenArr = useMemo(() => Children.toArray(children), [children]);

  // trigger autoplay
  useEffect(() => {
    if (!autoplay || !childrenArr.length) {
      return;
    }
    const timer = window.setTimeout(() => {
      setCurrentIndex(currentIndex === childrenArr.length - 1 ? 0 : currentIndex + 1);
    }, duration);
    return () => clearTimeout(timer);
  }, [autoplay, currentIndex, duration, childrenArr]);

  return (
    <div
      className={classNames}
      style={style}
    >
      <div
        className={styles.container}
        style={{
          transform: `translate(${-currentIndex * 100 + '%'}, 0)`,
          transitionDuration: `${speed}ms`,
          transitionTimingFunction: timingFunction,
        }}
      >
        {children}
      </div>
      {showIndicator && <ul className={styles.indicator}>
        {childrenArr.map((_, i) => (
          <li
            key={i}
            className={styles.indicator_item}
            onClick={() => setCurrentIndex(i)}
          >
            <div
              className={`${styles.indicator_item_inner} ${currentIndex === i ? styles.active : ''}`}
              style={{ animationDuration: autoplay ? `${duration}ms` : '0' }}
            ></div>
          </li>
        ))}
      </ul>}
    </div>
  )
}

const MemoizedCarousel = memo(Carousel) as React.NamedExoticComponent<PropsType> & { Item: typeof Item };

MemoizedCarousel.Item = Item;

export default MemoizedCarousel;