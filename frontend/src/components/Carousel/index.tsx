import { useEffect, useState, useRef, useCallback } from "react";
import "./index.scss";

export type CarouselItemProps = {
  id: number;
  img: string;
  title: string;
  desc: string;
  color: string;
  bgColor: string;
  itemWidth?: number;
};

type SliderProps = {
  stayTime: number;
  active: boolean;
  handleSliderClick: (index?: number) => void;
};

type CarouselProps = {
  items: CarouselItemProps[];
  stayTime: number; // 单个 banner 停留时间，单位:秒
};

// 轮播item
export const CarouselItem = ({
  title,
  img,
  desc,
  color,
  bgColor,
  itemWidth,
}: CarouselItemProps) => {
  return (
    <div
      className="carousel-item"
      style={{
        width: itemWidth,
        color,
        backgroundColor: bgColor,
      }}
    >
      <h1 className="carousel-title">{title}</h1>
      <p className="carousel-desc">{desc}</p>
      <img className="carousel-banner" src={img} alt="img" />
    </div>
  );
};

// 轮播标识
export const Slider = ({
  stayTime,
  active,
  handleSliderClick,
}: SliderProps) => {
  const handlerSlider = () => {
    handleSliderClick();
  };
  return (
    <div
      className={`carousel-slider ${active ? "active" : ""}`}
      onClick={handlerSlider}
    >
      <div
        className="carousel-progress"
        style={{ animationDuration: `${stayTime}s` }}
      />
    </div>
  );
};

export const Carousel = ({ items, stayTime }: CarouselProps) => {
  const [curIndex, setCurIndex] = useState(0);
  const itemWidth = document.documentElement.clientWidth;
  const itemsWidth = itemWidth * items.length;
  const timer = useRef<number>(0);

  // 定时器
  const timing = useCallback(() => {
    const run = () => {
      timer.current = window.setTimeout(() => {
        setCurIndex((curIndex + 1) % items.length);
      }, 1000 * stayTime);
    };
    run();
  }, [curIndex, items.length, stayTime]);

  useEffect(() => {
    timing();
    return () => {
      clearTimeout(timer.current);
    };
  }, [timing]);

  const handleSliderClick = (index: number) => {
    clearTimeout(timer.current);
    setCurIndex(index);
    timing();
  };

  return (
    <div className="carousel">
      <div className="carousel-content">
        <div
          className="carousel-items"
          style={{
            width: itemsWidth,
            transform: `translate3d(-${curIndex * itemWidth}px, 0px, 0px)`,
          }}
        >
          {items.map((item) => (
            <CarouselItem key={item.id} itemWidth={itemWidth} {...item} />
          ))}
        </div>
      </div>
      <div className="carousel-sliders">
        {items.map((item, index) => (
          <Slider
            key={item.id}
            stayTime={stayTime}
            active={index === curIndex}
            handleSliderClick={() => handleSliderClick(index)}
          />
        ))}
      </div>
    </div>
  );
};
