import React from 'react';
import { useState, useEffect, useMemo, useCallback, useRef, Children, cloneElement, memo, forwardRef, useImperativeHandle } from 'react';
import type { PropsType, RefType } from './index.d';
import { useUid, classNames } from './utils';
import Item from './Item';
import styles from './index.module.css';

const Carousel = forwardRef<RefType, PropsType>(({
  className,
  showIndicator = true,
  autoplay = false,
  duration = 3000,
  speed = 500,
  timingFunction = 'ease',
  children,
  onChange,
  indicatorRender,
  ...restProps
}, ref) => {
  // generate random uid
  const uid = useUid();
  // current visible item index, default 0
  const [currentIndex, setCurrentIndex] = useState<number>(0);
  // previous visible item index
  const prevIndex = useRef<number>(0);
  // children array
  const childrenArr = useMemo(() => Children.toArray(children), [children]);
  // autoplay timer handler
  let autoPlayTimer = useRef<number>();

  // change operation method
  const next = useCallback(() =>
    setCurrentIndex(index => index === childrenArr.length - 1 ? 0 : index + 1), [childrenArr]);
  const prev = useCallback(() =>
    setCurrentIndex(index => index === 0 ? childrenArr.length - 1 : index - 1), [childrenArr]);

  // trigger onChange callback when currentIndex change
  useEffect(() => {
    if (onChange && currentIndex !== prevIndex.current) {
      onChange.call(null, currentIndex, prevIndex.current);
    }
    prevIndex.current = currentIndex;
  }, [currentIndex, onChange]);

  // trigger autoplay
  useEffect(() => {
    if (autoplay && childrenArr.length > 1) {
      autoPlayTimer.current = window.setTimeout(next, duration);
    }
    return () => window.clearTimeout(autoPlayTimer.current);
  }, [autoplay, currentIndex, duration, childrenArr, next]);

  // expose methods to reference
  useImperativeHandle(ref, () => ({
    next,
    prev,
    goTo: (index: number) => setCurrentIndex(index),
  }), [next, prev]);

  // make accessibility
  const handleKeyDown = useCallback((e: React.KeyboardEvent<HTMLLIElement>) => {
    e.stopPropagation()
    if (e.code === 'Enter' || e.code === 'Space') e.currentTarget.click();
    if (e.code === 'ArrowRight') next();
    if (e.code === 'ArrowLeft') prev();
  }, [next, prev]);

  return (
    <div
      {...restProps}
      className={classNames(className, styles.carousel)}
    >
      <div
        className={styles.container}
        style={{
          transform: `translate(${-currentIndex * 100 + '%'}, 0)`,
          transitionDuration: `${speed}ms`,
          transitionTimingFunction: timingFunction,
        }}
      >
        {Children.map(children, (child, i) => {
          if (typeof child === 'undefined') return child;
          return cloneElement(child, {
            id: `carousel-item-${uid}-${i}`,
            role: 'tabpanel',
            'aria-labelledby': `carousel-indicator-${uid}-${i}`,
            'aria-hidden': currentIndex === i,
          });
        })}
      </div>
      {showIndicator && (indicatorRender
        ? indicatorRender(currentIndex, childrenArr.length)
        : <ul
          className={styles.indicator}
          role="tablist"
        >
        {childrenArr.map((_, i) => (
          <li
            key={i}
            id={`carousel-indicator-${uid}-${i}`}
            role="tab"
            aria-label={`change to page ${i + 1}`}
            aria-controls={`carousel-item-${uid}-${i}`}
            aria-selected={currentIndex === i}
            tabIndex={0}
            className={classNames(styles.indicator_item, {[styles.active]: currentIndex === i})}
            onClick={() => setCurrentIndex(i)}
            onKeyDown={handleKeyDown}
          >
            <div
              className={styles.indicator_item_inner}
              style={{ animationDuration: autoplay && childrenArr.length > 1 ? `${duration}ms` : '0' }}
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