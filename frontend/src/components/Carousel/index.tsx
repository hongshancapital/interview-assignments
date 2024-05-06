import { useEffect, useRef, useCallback } from "react";
import carousel from "./interface";
import useList from "../../hooks/useList";
import styles from "./index.module.css";

/**
 * 轮播组件
 */
const Carousel = ({
  showIndicator = true,
  autoPlay = true,
  className,
  duration = 3000,
  list,
}: carousel.CarouselProps) => {
  const intervalRef = useRef<number>();
  const [curIndex, onNext] = useList(list);
  // 定时切换面板动画
  useEffect(() => {
    if (autoPlay) {
      intervalRef.current = window.setTimeout(onNext, duration);
    }
    return () => clearTimeout(intervalRef.current);
  }, [duration, onNext, autoPlay]);

  return (
    <div className={`${styles.wrapper} ${className}`} data-testid='carousel'>
      <div className={styles.container} style={{ left: `-${100 * curIndex}%` }}>
        {list.map((item: carousel.CarouselItem, index: number) => (
          <Item
            key={item.title.join()}
            index={index}
            {...item}
            isActive={curIndex === index}
          />
        ))}
      </div>
      {showIndicator ? (
        <div className={styles.dots}>
          {list.map((item: carousel.CarouselItem, index: number) => (
            <Indicator
              key={index}
              isActive={curIndex === index}
              duration={duration}
              onNext={()=> onNext(index)}

            />
          ))}
        </div>
      ) : null}
    </div>
  );
};
/**
 * 轮播面板
 */
const Item = (props: carousel.CarouselItemProps & { index: number }) => {
  const { title, desc, imgUrl, color, bgColor, index } = props;
  const dom = useCallback((val: string[] | undefined) => {
    return val?.map((item, index) => <div key={index}>{item}</div>);
  }, []);
  return (
    <div
      className={styles.item}
      style={{
        backgroundImage: `url(${imgUrl})`,
        backgroundColor: bgColor,
        color: color,
        left: `${100 * index}%`,
      }}
    >
      <div className={styles.title}>{dom(title)}</div>
      <div className={styles.desc}>{dom(desc)}</div>
    </div>
  );
};
/**
 * 面板指示点
 */
const Indicator = (props: { isActive: boolean; duration: number, onNext:VoidFunction }) => {
  const { isActive, duration, onNext } = props;

  return (
    <div className={styles.dotsitem} >
      <button onClick={onNext}>1</button>
      <div
        className={styles.dotsscroller}
        style={isActive?{
          animation: `${duration}ms linear 0s  forwards running ${styles.progress}`,
        }: {}}
      />
    </div>
  );
};
export default Carousel;
