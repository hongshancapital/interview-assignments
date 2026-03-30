import {
  ReactNode,
  useEffect,
  useLayoutEffect,
  useMemo,
  useRef,
  useState,
} from "react";
import Indicator from "./Indicator";
import classes from "./index.module.scss";

export interface CarouselProps {
  children: ReactNode[];
  duration?: number;
}

const DEFAULT_DURATION = 2500;
const INIT_INDEX = -1;

const Carousel = (props: CarouselProps) => {
  const { duration = DEFAULT_DURATION, children = [] } = props;

  const containerRef = useRef<HTMLDivElement>(null);
  const [unitWidth, setUnitWidth] = useState<number>(0);
  const [activeIndex, setActiveIndex] = useState<number>(INIT_INDEX);

  const childLen = children.length;

  // 加载第一次「指示器」的动画
  useEffect(() => {
    setActiveIndex(0);
  }, []);

  useEffect(() => {
    let timer: NodeJS.Timeout | null = null;

    const fireTimer = () => {
      timer = setTimeout(() => {
        setActiveIndex((i) => (i + 1) % childLen);
        fireTimer();
      }, duration);
    };

    fireTimer();

    return () => {
      if (timer) clearTimeout(timer);
    };
  }, [duration, childLen]);

  useLayoutEffect(() => {
    const containerWidth = containerRef.current?.getBoundingClientRect().width;
    if (containerWidth !== undefined) setUnitWidth(containerWidth);
  }, []);

  const wrapperStyle = useMemo(
    () => ({
      width: unitWidth * childLen,
      left: (() => {
        if (activeIndex === INIT_INDEX) {
          return 0;
        }
        return -activeIndex * unitWidth;
      })(),
    }),
    [activeIndex, childLen, unitWidth]
  );

  const unitStyle = useMemo(
    () => ({
      width: unitWidth,
    }),
    [unitWidth]
  );

  return (
    <div className={classes["carousel-viewport"]} ref={containerRef}>
      <div className={classes["carousel-wrapper"]} style={wrapperStyle}>
        {props.children.map((child) => (
          <div className={classes["carousel-unit"]} style={unitStyle}>
            {child}
          </div>
        ))}
      </div>
      <div className={classes["carousel-indicator"]}>
        {children.map((_, i) => (
          <Indicator key={i} duration={duration} active={i === activeIndex} />
        ))}
      </div>
    </div>
  );
};

export default Carousel;
