import { useState, useEffect, useMemo, useRef, Children, memo, forwardRef, useImperativeHandle } from 'react';
import type { PropsType, RefType } from './index.d';
import Item from './Item';
import styles from './index.module.css';

const Carousel = forwardRef<RefType, PropsType>(({
  className,
  style = null,
  showIndicator = true,
  autoplay = false,
  duration = 3000,
  speed = 500,
  timingFunction = 'ease',
  children,
  indicatorRender,
}, ref) => {
  // combine classes
  const classNames = useMemo(() => className ? `${className} ${styles.carousel}` : styles.carousel, [className]);
  // current visible item index, default 0
  const [currentIndex, setCurrentIndex] = useState<number>(0);
  // children array
  const childrenArr = useMemo(() => Children.toArray(children), [children]);
  // autoplay timer handler
  let autoPlayTimer = useRef<number>()

  // trigger autoplay
  useEffect(() => {
    if (!autoplay || !childrenArr.length) {
      return;
    }
    autoPlayTimer.current = window.setTimeout(() => {
      setCurrentIndex(currentIndex === childrenArr.length - 1 ? 0 : currentIndex + 1);
    }, duration);
    return () => window.clearTimeout(autoPlayTimer.current);
  }, [autoplay, currentIndex, duration, childrenArr]);

  // expose methods to reference
  useImperativeHandle(ref, () => ({
    next: () => setCurrentIndex(currentIndex === childrenArr.length - 1 ? 0 : currentIndex + 1),
    prev: () => setCurrentIndex(currentIndex === 0 ? childrenArr.length - 1 : currentIndex - 1),
    goTo: (index: number) => setCurrentIndex(index),
  }), [childrenArr, currentIndex]);

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
      {showIndicator && (indicatorRender ? indicatorRender(currentIndex, childrenArr.length) : <ul className={styles.indicator}>
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
      </ul>)}
    </div>
  )
});

const MemoizedCarousel = memo(Carousel) as React.MemoExoticComponent<typeof Carousel> & { Item: typeof Item };

MemoizedCarousel.Item = Item;

export default MemoizedCarousel;