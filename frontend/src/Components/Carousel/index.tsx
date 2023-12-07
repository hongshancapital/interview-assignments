import React, {
  useCallback,
  useEffect,
  useMemo,
  useRef,
  useState,
} from "react";
import CarouselHandle from "./carouselHandle";
import "./carousel.scss";

interface SourceInfo {
  bgPath?: string;
  bgStyle?: object;
  content?: React.ReactNode;
}

interface CarouselProps {
  source: (string | SourceInfo)[];
  height?: number | "full";
  easing?: "linear" | "ease" | "ease-in" | "ease-out" | "ease-in-out";
  speed?: number;
  delay?: number;
}

const Carousel: React.FC<CarouselProps> = (props) => {
  const {
    height = "full",
    source,
    delay = 3000,
    speed = 500,
    easing = "ease",
  } = props;
  // 当前
  const [current, setCurrent] = useState(0);
  const currentRef = useRef<number>(current);
  // 组件高度
  const heightStyle: object = useMemo(() => {
    return { height: height === "full" ? "100%" : `${height}px` };
  }, [height]);
  // 切换方法
  const next = useCallback(() => {
    let current = currentRef.current;
    if (current < source.length - 1) {
      current = current + 1;
    } else {
      current = 0;
    }
    currentRef.current = current;
    setCurrent(current);
  }, [source]);
  // 定时器
  useEffect(() => {
    let timmer = setInterval(next, delay);
    return () => {
      timmer && clearInterval(timmer);
    };
  }, [delay, next]);
  // 容器样式 这里如果直接使用js操作domCss性能会更好
  const contentStyle = useMemo(
    () => ({
      width: `${100 * source.length}%`,
      left: `-${100 * current}%`,
      transitionDuration: `${speed / 1000}s`,
      transitionTimingFunction: easing,
    }),
    [source.length, current, speed, easing]
  );
  // 获取单项配置
  const getItemOpt = useCallback((item: string | SourceInfo) => {
    let itemStyle: object = {};
    let content: React.ReactNode = null;
    if (typeof item === "string") {
      Object.assign(itemStyle, { backgroundImage: `url(${item})` });
    } else {
      let { bgPath, bgStyle } = item;
      Object.assign(itemStyle, { backgroundImage: `url(${bgPath})` }, bgStyle);
      content = item.content;
    }
    return { itemStyle, content };
  }, []);
  return (
    <div className="carousel" style={heightStyle}>
      {source && source.length > 0 && (
        <div className="carousel-content" style={contentStyle}>
          {source.map((item, index) => {
            let { itemStyle, content } = getItemOpt(item);
            return (
              <div className="carousel-item" key={index} style={itemStyle}>
                {content}
              </div>
            );
          })}
        </div>
      )}
      <CarouselHandle length={source.length} current={current} speed={delay} />
    </div>
  );
};

export default Carousel;
