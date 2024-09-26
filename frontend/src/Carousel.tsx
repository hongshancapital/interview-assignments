import React, { useEffect, useRef, useState } from 'react';
import cls from 'classnames';
import styles from './carousel.module.css';

interface Props {
  /**
   * 切换间隔
   */
  interval?: number;
  /**
   * 切换速度, ms
   */
  speed?: number;
}

export default function Carousel({
  children,
  interval = 3000,
  speed = 1000,
}: React.PropsWithChildren<Props>) {
  const [width, setWidth] = useState(0);
  const containerRef = useRef<HTMLDivElement>(null);
  const [index, setIndex] = useState(0);

  const total = React.Children.count(children);

  useEffect(() => {
    setWidth(containerRef.current!.getBoundingClientRect().width)
  }, []);

  useEffect(() => {
    const timer = setInterval(() => {
      setIndex((i) => (i + 1) % total);
    }, interval);

    return () => {
      clearInterval(timer);
    };
  }, [interval, total]);

  return (
    <div ref={containerRef} className={styles.container}>
      <div
        className={styles.body}
        style={{
          transitionDuration: `${speed}ms`,
          transform: `translate(-${index * width}px)`,
        }}
      >
        {React.Children.map(children, (child) => (
          <div className={styles.item} style={{width}}>{child}</div>
        ))}
      </div>
      <div className={styles.footer}>
        {React.Children.map(children, (child, i) => {
          return (
            <div
              className={cls(styles.indicator, i === index && styles.progressing)}
              style={{ '--carousel-interval': `${interval}ms` }}
            ></div>
          );
        })}
      </div>
    </div>
  );
}
