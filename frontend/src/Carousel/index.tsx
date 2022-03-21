import React, {
  FC,
  CSSProperties,
  useRef,
  useEffect,
  useLayoutEffect,
  useState,
  useMemo,
} from "react";
import "./style.css";

interface CarouselProps {
  className?: string;
  style?: CSSProperties;
  children: JSX.Element[];
  autoplay?: boolean;
  duration?: number; // 每张幻灯片的持续时间
  interval?: number; // 幻灯片切换的间隔时间
}

function _classnames(...args: any[]): string {
  let classArr: string[] = [];
  for (let i = 0; i < args.length; i++) {
    let arg: any = args[i];
    if (!arg) {
      continue;
    }
    let argType = typeof arg;
    if (argType === "string" || argType === "number") {
      classArr.push(arg);
    } else if (Array.isArray(arg) && arg.length) {
      classArr.push(...arg);
    } else if (argType === "object") {
      for (let key in arg) {
        if (arg[key]) {
          classArr.push(key);
        }
      }
    }
  }
  return classArr.join(" ");
}

const Carousel: FC<CarouselProps> = ({
  className,
  style,
  children,
  autoplay = true,
  duration = 3000,
  interval = 300,
}) => {
  let carouselRef = useRef<HTMLDivElement>(null);
  let contentRef = useRef<HTMLDivElement>(null);
  let len = children.length;
  let dots = useMemo(() => {
    return new Array(len).fill(0);
  }, [len]);

  const [containerWidth, setContainerWidth] = useState<number>(0);
  const [currentIndex, setCurrentIndex] = useState<number>(0);
  const [timer, setTimer] = useState<any>(null);
  useLayoutEffect(() => {
    let carousel = carouselRef.current;
    setContainerWidth(carousel?.clientWidth || 0); // 获取当前容器宽度
  }, []);

  // 滑动动画
  useEffect(() => {
    let _timer: any;
    if (autoplay) {
      _timer = setInterval(() => {
        setCurrentIndex((c: number) => (c + 1) % len);
      }, duration);
      setTimer(_timer);
    }
    return () => {
      clearInterval(_timer);
    };
  }, [autoplay, duration, len, setTimer]);

  useEffect(() => {
    let content = contentRef.current;
    if (content) {
      let _translateX = -currentIndex * containerWidth;
      content.style.transform = `translateX(${_translateX}px)`;
    }
  }, [containerWidth, currentIndex]);

  const handleClick = (index: number) => {
    setCurrentIndex(index);
    clearInterval(timer);
    let _timer = setInterval(() => {
      setCurrentIndex((c: number) => (c + 1) % len);
    }, duration);
    setTimer(_timer);
  };

  return (
    <div
      ref={carouselRef}
      className={_classnames("carousel-container", className)}
      style={style || {}}
    >
      <div
        className="carousel-content"
        ref={contentRef}
        style={{
          width: containerWidth * len,
          transition: `transform ${interval}ms linear`,
        }}
      >
        {children.map((child, index) => {
          return (
            <div key={index} style={{ height: "100%", width: containerWidth }}>
              {child}
            </div>
          );
        })}
      </div>
      <div className="dots-wrapper">
        {dots.map((_, index) => (
          <div
            key={index}
            onClick={() => handleClick(index)}
            className={_classnames("dot-item", {
              active: index === currentIndex,

            })}
          >
            <div
              className="dot-animate"
              style={{
                animationDuration: `${duration}ms`,
              }}
            ></div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Carousel;
