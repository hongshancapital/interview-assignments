import classNames from "classnames";
import React, { CSSProperties, ReactElement, useState } from "react";
import usePlay from "../../hooks/use-play";
import './index.css';

export interface ICarouselProps {
  items: ICarouselItem[];
  duration?: number;
  hoverstop?: boolean;
  style?: CSSProperties;
  className?: string;
}

export interface ICarouselItem {
  title?: string | ReactElement;
  text?: string | ReactElement;
  classname?: string;
  children?: ReactElement;
}

/**
 * 暴露给外部使用的Carousel组件，主体分为两部分：图片容器和页码指示器。自定义的usePlay控制二者的切换节奏。
 * @param props 组件属性
 * @returns 组件
 */
const Carousel = (props: ICarouselProps) => {
  const { duration = 3000, items, hoverstop = true, className, style } = props;
  const { index, timer, setCurrentStep } = usePlay(items.length, duration);
  const [stop, setStop] = useState(false);

  return (
    <div className={classNames("container", className)} style={style}>
      <CarouselContent
        {...{ items, index }}
        onMouseEnter={() => {
          if (hoverstop && timer?.current) {
            clearInterval(timer.current);
            setStop(true);
          }
        }}
        onMouseLeave={() => {
          if (hoverstop) {
            setCurrentStep({ index });
            setStop(false);
          }
        }}
      />
      <CarouselIndex {...{ items, index, stop, duration }} />
    </div>
  );
}

/**
 * 图片容器，实现原理：n张图片横向铺开，整个容器的宽度是父元素的n倍，切换动作就是容器向左移动1/n倍的总宽度。
 * @param props 容器属性
 * @returns 容器组件
 */
const CarouselContent = (props: { items: ICarouselItem[]; index: number; } & React.DOMAttributes<HTMLDivElement>) => {
  const { items, index } = props;

  return (
    <div className="content" style={{ transform: `translateX(${-index / items.length * 100}%)`, width: `${items.length * 100}%` }} {...props}>
      {items.map((item, i) => {
        return (
          <div key={`carousel_${i}`} className={classNames("item", item.classname)}>
            <div className="title pre" style={{ marginTop: "10vh" }}>{item.title}</div>
            <div className="text pre">{item.text}</div>
            {item.children}
          </div>
        );
      })}
    </div>
  );
}

/**
 * 页码指示器，进度动画采用CSS动画实现，宽度从0% -> 100%.周期即图片切换周期。
 * @param props 页码指示属性
 * @returns 页码器元素组件
 */
const CarouselIndex = (props: { index: number; items: ICarouselItem[]; stop?: boolean; duration: number }) => {
  const { items, index, duration, stop } = props;

  return (
    <div className="index">
      {items.map((item, i) => {
        return (
          <div key={`index-item_${i}`} className="index-item">
            <div
              className={classNames("index-progress", { "index-progress-active": index === i && !stop })}
              style={{ animationDuration: `${duration / 1000}s` }}
            />
          </div>
        );
      })}
    </div>
  );
}

export default Carousel;