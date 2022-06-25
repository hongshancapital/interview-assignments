/*
 * @Author: danjp
 * @Date: 2022/6/23 17:33
 * @LastEditTime: 2022/6/23 17:33
 * @LastEditors: danjp
 * @Description:
 */
import React, { useEffect, useMemo, useRef } from 'react';
import styles from './CarouselDots.module.scss';

type CarouselDotsProps = {
  current: number;
  count: number;
  duration: false|number;
  // 指示符动画过渡完成事件
  onTransitionEnd(): void;
}

const CarouselDotsContent: React.FC<CarouselDotsProps> = (props) => {
  const { current, count, duration, onTransitionEnd } = props;
  const list = useMemo(() => Array.from({ length: count }), [count]);
  
  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <div className={styles.container__page}>
          {list.map((_, index) => (
            <div key={index} className={styles.container__page__item} />
          ))}
        </div>
        <div className={styles.container__barWrapper}>
          {list.map((_, index) => (
            <Bar key={index} duration={duration} current={current} activeIndex={index} onTransitionEnd={onTransitionEnd} />
          ))}
        </div>
      </div>
    </div>
  );
};

const CarouselDots: React.FC<CarouselDotsProps> = (props) => {
  if (props.count <= 1) return null;
  return <CarouselDotsContent {...props} />
};

export default CarouselDots;

type BarProps = Omit<CarouselDotsProps, 'count'> & {
  activeIndex: number;
}
const Bar: React.FC<BarProps> = ({ duration, current, activeIndex, onTransitionEnd }) => {
  const progressRef = useRef<HTMLDivElement | null>(null)
  
  useEffect(() => {
    const dom = progressRef.current;
    if (!dom) return;
    if (!duration) return;
  
    if (current === activeIndex) {
      dom.style.width = '100%';
      dom.style.transition = `width ${duration}ms`;
    } else {
      dom.style.width = '0';
      dom.style.transition = 'width 0ms';
    }
    onTransitionEnd();
    /* eslint-disable react-hooks/exhaustive-deps */
  }, [current, activeIndex])
  
  return (
    <div className={styles.container__barWrapper__bar}>
      <div ref={progressRef} className={styles.container__barWrapper__bar__progress} onTransitionEnd={onTransitionEnd} />
    </div>
  )
};
