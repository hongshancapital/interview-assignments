import React, { useEffect, useState, useRef } from "react";
import classnames from 'classnames'
import styles from "./Carousel.module.css";

export interface ListItem {
  key: number,
  url: string,
  title?: string,
  subTitle?: string,
  text?: string,
  des?: string,
  color?: string,
}
interface CarouselProps {
  children: React.ReactNode;
  duration?: number;
  delay?: number;
};
function Carousel ({ duration = 3000, delay = 1000, children }: CarouselProps) {

  const items = Array.isArray(children) ? children : [children];

  // Set the switching time of pictures and buttons respectively
  const dotEl = useRef<HTMLDivElement | null>(null);
  useEffect(() => {
    dotEl?.current?.style.setProperty('--timer-delay', `${(delay / 1000).toFixed(3)}s`)
    dotEl?.current?.style.setProperty('--timer-duration', `${(duration / 1000).toFixed(3)}s`)
  }, [duration, delay])


  /**
   * set driving time with funtcion setInterval, and save in tabIndex. 
   * the annimation is drived by tabIndex.
   */
  const [tabIndex, setTabIndex] = useState<number>(0)
  useEffect(() => {
    const id: NodeJS.Timer = setInterval(() => {
        setTabIndex(tabIndex => {
          if (tabIndex >= items.length - 1) {
            return 0
          } else {
            return tabIndex + 1;
          }
        });
    }, duration);
    return () => {
      clearInterval(id)
    }
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])
  return (
    <div ref={dotEl} className={styles.carouselContent}>
      <ul className={styles.carouselUl}>
        {
          items.map((child: React.ReactNode, index: number) => (
            <li
              className={ classnames(styles.carouselLi, {
                [styles.carouselLiBefore]: index < tabIndex,
                [styles.carouselLiActive]: index === tabIndex,
                [styles.carouselLiAfter]: index > tabIndex,
              })}
              key={index}
            >{child}</li>
          ))
        }
      </ul>
      <div className={styles.carouselBar}>
        {
          items.map((child: React.ReactNode, index: number) => (
            <div key={index} className={styles.carouselDot}>
              <div className={ classnames({
                [styles.carouselDotPregress]: tabIndex === index
              })}></div>
            </div>
          ))
        }
      </div>
    </div>
  );
}

export default Carousel;