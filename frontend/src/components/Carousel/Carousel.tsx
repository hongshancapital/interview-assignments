import React, {
  useMemo,
  useState,
  useEffect,
  useCallback,
  useRef,
  forwardRef,
  useImperativeHandle,
} from "react";
import "./style.scss";

export type CarouselProps = {
  carouselStyle?: React.CSSProperties;
  carouselContainerStyle?: React.CSSProperties;
  carouselItemStyle?: React.CSSProperties;
  activeIndex?: number;
  width: number;
  height: number;
  autoplay?: boolean;
  className?: string;
  onChange?: (activeIndex: number) => void;
  /** 切换间隔单位ms **/
  delay?: number;
  /** 过渡时间单位ms **/
  speed?: number;
};

const Carousel: React.FC<CarouselProps> = forwardRef((props, ref) => {
  const [activeIndex, setActiveIndex] = useState<number>(
    () => props.activeIndex ?? 0
  );
  const {
    children,
    width,
    height,
    autoplay = true,
    delay = 2000,
    speed = 300,
    className,
  } = props;
  const carouselStyle = useMemo(
    () =>
      Object.assign({}, props.carouselStyle ?? {}, {
        width: `${width}px`,
        height: `${height}px`,
      }),
    [props.carouselStyle, height, width]
  );

  const carouselItemStyle = useMemo(
    () =>
      Object.assign({}, props.carouselItemStyle ?? {}, {
        width: `${width}px`,
        height: `${height}px`,
      }),
    [props.carouselItemStyle, height, width]
  );

  // 重新包装children
  const newChildren = useMemo(() => {
    const _children = React.Children.toArray(children).filter((v) => !!v);
    return _children.map((dom, index) => (
      <div
        data-index={index}
        data-active={activeIndex === index}
        tabIndex={-1}
        className="my-carousel-item"
        style={carouselItemStyle}
        key={index}
      >
        {dom}
      </div>
    ));
  }, [children, activeIndex, carouselItemStyle]);

  const carouselContainerStyle = useMemo(
    () =>
      Object.assign({}, props.carouselContainerStyle ?? {}, {
        transform: `translate3d(-${activeIndex * width}px, 0px, 0px)`,
        width: `${newChildren.length * width}px`,
        height: `${height}px`,
        transition: `all ${speed}ms`,
      } as React.CSSProperties),
    [
      props.carouselContainerStyle,
      activeIndex,
      width,
      newChildren.length,
      height,
      speed,
    ]
  );

  const timer = useRef<NodeJS.Timeout>();
  const startAutoplay = useCallback(() => {
    timer.current = setTimeout(() => {
      setActiveIndex((activeIndex + 1) % newChildren.length);
      startAutoplay();
    }, delay);
  }, [delay, activeIndex, newChildren.length]);

  const stopAutoplay = useCallback(() => {
    timer?.current && clearTimeout(timer.current!);
  }, []);

  const restartAutoplay = useCallback(() => {
    timer?.current && clearTimeout(timer.current!);
    startAutoplay();
  }, [startAutoplay]);
  const handleOnChange = useCallback(
    (newActiveIndex) => {
      if (activeIndex !== newActiveIndex) {
        setActiveIndex(newActiveIndex);
        props?.onChange?.(newActiveIndex);
        restartAutoplay();
      }
    },
    [activeIndex, props, restartAutoplay]
  );

  const dots = useMemo(() => {
    return new Array(newChildren.length).fill("").map((_, index) => (
      <div
        data-index={index}
        data-active={activeIndex === index}
        className="my-carousel-dot "
        onClick={() => handleOnChange(index)}
        key={index}
      >
        <span
          style={{
            animationDuration: `${delay}ms`,
            animationPlayState:activeIndex === index  ?'running' :'paused'
          }}
        ></span>
      </div>
    ));
  }, [activeIndex, handleOnChange, newChildren.length, delay]);

  useEffect(() => {
    if (autoplay) {
      startAutoplay();
    } else {
      stopAutoplay();
    }
    return () => {
      stopAutoplay();
    };
  }, [autoplay, startAutoplay, stopAutoplay]);

  // activeIndex 受控
  useEffect(() => {
    if (props.activeIndex && props.activeIndex !== activeIndex) {
      setActiveIndex(props.activeIndex!);
    }
  }, [activeIndex, props.activeIndex]);

  useImperativeHandle(ref, () => ({
    goTo(newActiveIndex: number) {
      handleOnChange(newActiveIndex);
    },
    next() {
      handleOnChange((activeIndex + 1) % newChildren.length);
    },
    prev() {
      handleOnChange((activeIndex - 1) % newChildren.length);
    },
    startAutoplay() {
      startAutoplay();
    },
    stopAutoplay() {
      stopAutoplay();
    },
    restartAutoplay() {
      restartAutoplay();
    },
  }));

  return (
    <div
      className={["my-carousel", className].join(" ")}
      data-autoplay={!!autoplay}
      style={carouselStyle}
    >
      <div className="my-carousel-container" style={carouselContainerStyle}>
        {newChildren}
      </div>
      <div className="my-carousel-dots">{dots}</div>
    </div>
  );
});

export default Carousel;
