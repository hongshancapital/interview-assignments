import React, {
  FC,
  useEffect,
  useCallback,
  useState,
  useRef,
  ReactNode,
  ReactElement
} from "react";
import styles from "./style.module.scss";
import CarouselItem from "../CarouselItem";

interface CarouselProps {
  // 走马灯的高度
  height?: string;
  //  是否自动播放
  autoplay?: boolean;
  // 切换动画持续时间ms
  duration?: number;
  // 播放间隔ms
  interval?: number;
  // 初始的激活下标
  initialIndex?: number;
  // 是否循环播放
  loop?:boolean;
  // 子节点
  children?: ReactNode;
}
const Carousel: FC<CarouselProps> = ({
  height = "200px",
  autoplay = true,
  duration = 300,
  interval = 3000,
  initialIndex = 0,
  loop = true,
  children,
}) => {
  let [activeIndex, setActiveIndex] = useState<number>(initialIndex);
  const timer: any = useRef();
//   限制子节点类型
  let child: ReactNode[] = [];
  React.Children.forEach(children,(el)=>{
    if((el as ReactElement)?.type === CarouselItem) child.push(el);
  });
  const len = child.length;
  // 设置激活项
  let setActiveItem = useCallback(
    (index: number) => {
      if (index < 0) {
        index = len - 1;
      } else if (index >= len) {
        index = loop ? 0 : len - 1;
      };
        setActiveIndex(index);
    },
    [len, loop]
  );
  // 播放
  let startTimer = useCallback(() => {
    timer.current = setTimeout(() => {
      setActiveItem(activeIndex + 1);
    }, interval);
  }, [setActiveItem, activeIndex, interval]);
  // 停止
  let pauseTimer = useCallback(() => {
    clearTimeout(timer.current);
    timer.current = null;
  }, []);

  useEffect(() => {
    if (interval <= 0 || !autoplay) return;
    pauseTimer();
    startTimer();
    return () => {
      pauseTimer();
    };
  }, [startTimer, pauseTimer, interval, autoplay]);

  return (
    <div
      data-testid="test-carousel-id"
      className={styles["carousel"]}
      style={{ height: height, "--timer-duration": `${duration}ms` }}
    >
      <div
        className={styles["carousel-container"]}
        style={{ transform: `translateX(${-activeIndex}00%)` }}
      >
        {child}
      </div>
      {/* 指示器 */}
      <ul className={styles["indicator"]}>
        {child.map((item, index) => {
          return (
            <li
              key={index}
              className={`${styles["indicator-item"]} ${
                index === activeIndex ? styles["is-active"] : ""
              }`}
              onClick={() => setActiveItem(index)}
            >
              <button className={styles["indicator-button"]}>
                <span style={{animationDuration:`${interval}ms`}}></span>
              </button>
            </li>
          );
        })}
      </ul>
    </div>
  );
};
export default Carousel;
