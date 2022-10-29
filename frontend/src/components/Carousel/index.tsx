import React, { useEffect, useRef, useState } from "react";
import Slice, { SliceProps } from "./Slice";
import "./index.css";
import Legend from "./Legend";

interface CarouselProps {
  data: SliceProps[];
  defaultIndex?: number;
  wrapperClass?: string;
  duration?: number;
  autoPlay?: boolean;
}
const CONTAINER_WIDTH = 1000;
const Carousel: React.FC<CarouselProps> = ({
  data,
  defaultIndex = 0,
  wrapperClass = "",
  duration = 3,
  autoPlay = true,
}) => {
  const [activeIndex, setActiveIndex] = useState<number>(defaultIndex);
  const [translateX, setTranslateX] = useState<number>(0);
  const count = data?.length || 0;
  const [timer, setTimer] = useState<number>(0);

  useEffect(() => {
    let _timer: number;
    if (count > 1 && autoPlay) {
      let _timer = window.setInterval(() => {
        setActiveIndex((current) => (current + 1) % count);
      }, duration * 1000);
      setTimer(_timer);
    }
    return () => clearInterval(_timer);
  }, [data, duration, autoPlay, count]);

  const handleLegendClick = (index: number) => {
    setActiveIndex(index);
    clearInterval(timer);
    let _timer: number = window.setInterval(() => {
      setActiveIndex((cur: number) => (cur + 1) % count);
    }, duration * 1000);
    setTimer(_timer);
  };

  useEffect(() => {
    setTranslateX(-(CONTAINER_WIDTH * activeIndex));
  }, [activeIndex]);

  return (
    <div className={`carousel-container ${wrapperClass}`}>
      <div
        className="slice-wrap"
        style={{
          width: CONTAINER_WIDTH * count,
          transition: `transform 1s`,
          transform: `translateX(${translateX}px)`,
        }}
      >
        {data?.map((item: SliceProps, index: number) => {
          let {
            title,
            titleStyle,
            subtitle,
            image,
            subtitleStyle,
            backgroundStyle,
          } = item;
          return (
            <Slice
              title={title}
              titleStyle={titleStyle}
              subtitle={subtitle}
              subtitleStyle={subtitleStyle}
              backgroundStyle={backgroundStyle}
              image={image}
              key={`slice${index}`}
            />
          );
        })}
      </div>
      <div className="bottom-legend">
        {data?.length > 1 &&
          data.map((_, index) => {
            return (
              <Legend
                key={`legend${index}`}
                index={index}
                activeIndex={activeIndex}
                duration={duration}
                onLegendClick={handleLegendClick}
              ></Legend>
            );
          })}
      </div>
    </div>
  );
};
export default Carousel;
