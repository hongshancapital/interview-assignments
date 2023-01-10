import * as React from "react";
import clsx from "clsx";
import styles from "./Carousel.module.scss";
import { Pagination } from "./Pagination";
import { useMotion } from "./hooks";

interface ICarouselProps {
  children: Array<any>;
  className?: string;
  speed?: number;
  delay?: number;
}

export const Carousel: React.FC<ICarouselProps> = ({ children, className, speed = 500, delay = 3000 }) => {
  const childrenRef = React.useRef<any>([]);
  const count = React.Children.count(children);
  const [tranStyle, activeIndex] = useMotion(childrenRef, delay)

  return (
    <div className={clsx(styles.root, className)}>
      <div className={styles.content} style={{...tranStyle, transition: `transform ${speed}ms`}}>
        {React.Children.map(children, (child, index) =>
          React.cloneElement(child, {
            ref: (ref: any) => (childrenRef.current[index] = ref),
          })
        )}
      </div>
      <Pagination className={styles.pagination} total={count} activeIndex={activeIndex} delay={delay} />
    </div>
  );
};
