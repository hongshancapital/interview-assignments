/*
 * @Author: danjp
 * @Date: 2022/6/23 17:33
 * @LastEditTime: 2022/6/23 17:33
 * @LastEditors: danjp
 * @Description:
 */
import React, { useEffect, useMemo, useRef } from 'react';
import styles from './CarouselDots.module.scss';

export interface CarouselDotsProps {
  current: number;
  count: number;
  duration: number;
}

const CarouselDots: React.FC<CarouselDotsProps> = (props) => {
  const { current, count, duration } = props;
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
            <Bar key={index} current={current} activeIndex={index} />
          ))}
        </div>
      </div>
    </div>
  );
};

export default (props: CarouselDotsProps) => {
  if (props.count <= 1) return null;
  return <CarouselDots {...props} />
};

interface BarProps {
  current: number;
  activeIndex: number;
}
const Bar: React.FC<BarProps> = ({ current, activeIndex }) => {
  const progressRef = useRef<HTMLDivElement | null>(null)
  
  useEffect(() => {
    const dom = progressRef.current;
    if (!dom) return;
    
    if (current === activeIndex) {
      dom.style.width = '100%';
      dom.style.transition = 'width 2s';
    } else {
      dom.style.width = '0';
      dom.style.transition = 'width 0s';
    }
  }, [current, activeIndex])
  
  return (
    <div className={styles.container__barWrapper__bar}>
      <div ref={progressRef} className={styles.container__barWrapper__bar__progress} />
    </div>
  )
};
