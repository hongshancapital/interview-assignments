import React, { useRef, useEffect, useState } from "react";
import "./index.scss";
interface CarouselProps {
  children?: JSX.Element[] | null | JSX.Element;
  className?: string;
  style?: Object;
  id?: string;
  autoplay?: boolean;
  showPagination?: boolean;
  waitTime?: number;
}
interface SliderProps {
  children?: JSX.Element | null | string;
  className?: string;
  style?: Object;
  id?: string;
}
const Carousel: React.FC<CarouselProps> & { Slider: React.FC<SliderProps> } = (
  props
) => {
  const {
    children = [],
    autoplay = false,
    showPagination = false,
    waitTime = 4e3,
    ...rest
  } = props;
  if (typeof autoplay !== "boolean") {
    console.error("Warning: [Carousel] Invalid prop `autoplay`");
  }
  if (typeof showPagination !== "boolean") {
    console.error("Warning: [Carousel] Invalid prop `showPagination`");
  }
  if (typeof waitTime !== "number") {
    console.error("Warning: [Carousel] Invalid prop `waitTime`");
  }
  const [activeIndex, setActiveIndex] = useState(-1);
  const [isAutoPlay, setIsAutoPlay] = useState(autoplay);
  const timerRef = useRef<number>();
  const wrapperRef = useRef<HTMLDivElement | null>(null);
  useEffect(() => {
    setActiveIndex(0);
    if (isAutoPlay) {
      timerRef.current = window.setInterval(() => {
        setActiveIndex((val) => (val + 1) % 3);
      }, waitTime);
    }
    return () => {
      window.clearInterval(timerRef.current);
    };
  }, []);
  useEffect(() => {
    if (isAutoPlay) {
      wrapperRef.current!.setAttribute(
        "style",
        ` transform: translateX(-${
          100 * activeIndex
        }%) ;transition: transform 1s linear`
      );
    }
  }, [activeIndex]);
  function handleClick(index: number) {
    setIsAutoPlay(false);
    setActiveIndex(index);
    window.clearInterval(timerRef.current);
    wrapperRef.current!.setAttribute(
      "style",
      ` transform: translateX(-${100 * index}%) `
    );
  }
  return (
    <div className="banner" {...rest}>
      <div className="container ">
        <div
          className="wrapper"
          ref={wrapperRef}
          style={{
            transform: `translateX(0px)`,
            width: `${Array.isArray(children) ? 100 * children!.length : 100}%`,
          }}>
          {children}
        </div>
        {showPagination && (
          <ul
            className={`pagination  pagination-bullets ${
              isAutoPlay ? "autoplay" : ""
            }`}>
            {Array.isArray(children) &&
              children.map((item, index) => (
                <li
                  key={index}
                  onClick={() => {
                    handleClick(index);
                  }}
                  className={`pagination-bullet ${
                    isAutoPlay ? "" : index === activeIndex ? "active" : ""
                  } ${activeIndex === index ? "current" : ""}`}>
                  <span>
                    {/* tsignor */}
                    <i
                      style={
                        {
                          "--time": waitTime / 1e3 + "s",
                        } as React.CSSProperties
                      }></i>
                  </span>
                </li>
              ))}
          </ul>
        )}
      </div>
    </div>
  );
};
Carousel.Slider = ({ children, ...rest }) => {
  return (
    <div className="slider" {...rest}>
      {children}
    </div>
  );
};
export default Carousel;
