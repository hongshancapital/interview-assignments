import React, { useState, useEffect, ReactElement, useRef, useMemo } from "react";
import Progress from "./components/Progress";
import "./index.scss";

interface imagesProps {
  url: string;
  renderText: () => ReactElement;
}

interface CarouselProps {
  images: imagesProps[];
  interval?: number;
  width?: number;
  scrollerClassName?: string;
}

const Carousel: React.FC<CarouselProps> = ({ images, interval = 2000, width, scrollerClassName }) => {
  const [activeIndex, setActiveIndex] = useState(0);
  const scrollerRef = useRef<HTMLDivElement>(null);

  /**
   * 定时器
   */
  useEffect(() => {
    if (!interval) return;
    const timeout = setInterval(() => {
      setActiveIndex((activeIndex + 1) % images.length);
    }, interval);
    return () => {
      clearInterval(timeout)
    };
  }, [activeIndex, images.length]);

  /**
   * 计算偏移量
   */
  const diff = useMemo(() => {
    const _width = scrollerRef.current?.offsetWidth || width;
    if (_width) {
      return activeIndex * -_width + "px";
    }
  }, [activeIndex, width, scrollerRef])

  /**
   * 计算inner盒子宽度
   */
  const innerWidth = useMemo(() => {
    return `${images.length * 100}%`
  }, [images.length])

  
  return (
    <div className={`scroller ${scrollerClassName || ''}`} ref={scrollerRef} style={{width}}>
      <div
        className="inner"
        style={{
          transition: "1.5s ease",
          transform: `translateX(${diff})`,
          width: innerWidth,
        }}
      >
        {images.map((item, index) => {
          return (
            <div style={{ position: "relative", width: "100%" }} key={item.url}>
              <img src={require(`../../assets/${item.url}`)}  />
              {images[index].renderText()}
            </div>
          );
        })}
      </div>
      <Progress images={images} activeIndex={activeIndex} interval={interval} />
    </div>
  );
};

export default Carousel;
