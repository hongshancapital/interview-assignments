import * as React from "react";
import clsx from "clsx";
import styles from "./Carousel.module.scss";
import { Pagination } from "./Pagination";

interface ICarouselProps {
  children: Array<any>;
  className?: string;
  speed?: number;
  delay?: number;
}

export const Carousel: React.FC<ICarouselProps> = ({ children, className, speed = 500, delay = 3000 }) => {
  const [activeIndex, setActiveIndex] = React.useState<number>();
  const eleRef = React.useRef<HTMLDivElement>(null);
  const [tranStyle, setTranStyle] = React.useState<React.CSSProperties>();
  const childrenRef = React.useRef<any>([]);
  const count = React.Children.count(children);

  React.useEffect(()=> {
    setActiveIndex(0);
  }, [children, setActiveIndex])

  React.useEffect(() => {
    if(activeIndex === undefined) {
        return;
    }
    
    const elm = eleRef.current!;

    const timer = setTimeout(() => {
      const newActiveIndex = (activeIndex + 1) % children.length;

      if (elm.children[newActiveIndex]) {
        const activeEle = elm.children[newActiveIndex] as HTMLElement;
        const first = activeEle.getBoundingClientRect();
        activeEle.style.position = "absolute";
        activeEle.style.left = "0";
        const last = activeEle.getBoundingClientRect();
        const deltaX = last.left - first.left;

        activeEle.style.position = "";
        activeEle.style.left = "";
        setTranStyle({ transform: `translateX(${deltaX}px)` });
      }
     
      setActiveIndex(newActiveIndex);
    }, delay);

    return () => {
      clearTimeout(timer);
    };
  }, [activeIndex, setActiveIndex, children]);

  return (
    <div className={clsx(styles.root, className)}>
      <div ref={eleRef} className={styles.content} style={{...tranStyle, transition: `transform ${speed}ms`}}>
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
