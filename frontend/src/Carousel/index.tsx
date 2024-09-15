import React, { ReactNode } from "react";
import "./index.css";
import { useCarousel } from "./useCarousel";

export type SlideItem = {
  id: number;
  title: string;
  subTitle?: string;
  desc?: string;
  image?: string;
  bgColor?: string;
  color?: string;
};

type ICarouselProps = {
  slides: Array<SlideItem>; //轮播列表数据
  duration?: number; //每张图片展示时间，单位ms
  transationTime?: number; //每张图片切换时间，单位ms
};

type ICarouselItemProps = {
  children?: ReactNode;
};

interface ICarousel extends React.FC<ICarouselProps> {
  Item: React.FC<ICarouselItemProps>;
}

//渲染轮播内容样式
const renderCarouselInfo = (slide: SlideItem) => {
  const {
    title,
    subTitle,
    desc,
    image,
    bgColor = "#fff",
    color = "#000",
  } = slide;
  return (
    <div
      className="carousel-item_wrap"
      style={{ backgroundColor: bgColor, color: color }}
    >
      <div className="carousel-item_header">
        <div className="carousel-item_title">{title}</div>
        {subTitle && <div className="carousel-item_subtitle">{subTitle}</div>}
        {desc && <div className="carousel-item_desc">{desc}</div>}
      </div>

      {image && (
        <div className="carousel-item_bottomImg">
          <div
            className="carousel-item_bg"
            style={{ backgroundImage: `url(${image})` }}
          ></div>
        </div>
      )}
    </div>
  );
};

const CarouselItem: React.FC<ICarouselItemProps> = ({ children }) => {
  return (
    <div className="carousel-item" data-testid="carousel-item">
      {children}
    </div>
  );
};

const Carousel: ICarousel = ({
  slides = [],
  duration = 3000,
  transationTime = 1000,
}) => {
  const length = slides.length;
  const { curIndex, stepToNext, minTransitionTime } = useCarousel(
    length,
    duration,
    transationTime
  );
  //轮播容器样式
  const containerStyle: React.CSSProperties = {
    transform: `translateX(${-curIndex * 100}%)`,
    transitionDuration: `${minTransitionTime / 1000}s`,
  };

  //进度条样式
  const progressStyle = {
    animationName: "slide-animation",
    animationDuration: `${duration / 1000}s`,
  };
  return (
    <div className="carousel" data-testid="carousel">
      <div className="carousel-content" style={containerStyle}>
        {slides.map((slide, index) => {
          return (
            <Carousel.Item key={index}>
              {renderCarouselInfo(slide)}
            </Carousel.Item>
          );
        })}
      </div>
      <div className="carousel-indicators">
        {slides.map((slide, index) => {
          return (
            <div
              className="carousel-indicator"
              key={index}
              data-testid="carousel-indicator"
            >
              <div
                className="carousel-indicator_inside"
                style={curIndex === index ? progressStyle : {}}
                onAnimationEnd={() => stepToNext()}
              ></div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

Carousel.Item = CarouselItem;

export default Carousel;
