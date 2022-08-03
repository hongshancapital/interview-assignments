import React, { useCallback, useState, useEffect, useRef } from "react";
import styles from "./index.module.scss";

interface IProps {
  interval?: number,
  showIndicator?: boolean,
  onChange?: (index: number) => void
}


const Carousel: React.FC<IProps> = (props) => {
  const [current, setCurrent] = useState<number>(-1);
  const timerRef = useRef<NodeJS.Timeout | undefined>();

  const slideCount = React.Children.count(props.children);
  const showIndicator = props.showIndicator !== false;
  const interval = props.interval || 2000;


  const slide = useCallback(() => {
    setCurrent((curIndex: number) => {
      const nextIndex: number = curIndex === slideCount - 1 ? 0 : curIndex + 1;
      props.onChange && props.onChange(nextIndex);
      return nextIndex;
    });

  }, [slideCount, props])


  useEffect(() => {
    timerRef.current = setInterval(slide, interval);
    slide();
    return () => {
      timerRef.current && clearInterval(timerRef.current)
    }
  }, [slide, interval])


  if (slideCount === 0) {
    return null
  }

  return <div className={styles.carousel_con}>
    <div className={styles.carousel} style={{ transform: `translateX(-${current * 100}%)` }}>
      {
        React.Children.map(props.children, (item, index) => {
          return <div className={styles.carousel_item} key={index}>
            {item}
          </div>
        })
      }
    </div>

    {showIndicator && (<div className={styles.carousel_indicator}>
      {React.Children.map(props.children, (item, index: number) => {
        const active = index === current;
        return <div key={index} className={`${styles.carousel_indicator_el} ${active ? styles.indicator_active : ''}`} >
          {
            active && <div className={styles.progress} style={{ animationDuration: `${interval}ms` }}></div>
          }
        </div>

      })}
    </div>)}
  </div>
}

export default Carousel;
