import React, { FC, CSSProperties } from "react";
import useCarousel from "./useCarousel";
import "./index.css";

interface ICarouselItemProps {
  imgProps: {
    src: string;
    alt?: string;
    style?: CSSProperties;
  };
  titleProps?: {
    content?: string[];
    style?: CSSProperties;
  };
  descProps?: {
    content?: string[];
    style?: CSSProperties;
  };
  wrapperStyle?: CSSProperties;
}

export interface ICarouselProps {
  items: ICarouselItemProps[];
  wrapperStyle?: CSSProperties;
  /**
   * 轮播间隔，单位 ms
   * @default 3000
   */
  ms?: number;
}

const DEFAULT_MS = 3000;

/**
 * 轮播图项
 */
const CarouselItem: FC<ICarouselItemProps> = ({
  imgProps,
  titleProps,
  descProps,
  wrapperStyle,
}) => {
  return (
    <div className="carousel-item" style={wrapperStyle}>
      <div className="content">
        <div className="title" style={titleProps?.style}>
          {titleProps?.content?.map((c, idx) => (
            <div key={`title_${idx}`}>{c}</div>
          ))}
        </div>
        <div className="desc" style={descProps?.style}>
          {descProps?.content?.map((c, idx) => (
            <div key={`desc_${idx}`}>{c}</div>
          ))}
        </div>
      </div>
      <img {...imgProps} alt={imgProps.alt} />
    </div>
  );
};

/**
 * 轮播图进度条
 */
const CarouselProgress: FC<{
  count: number;
  active: number;
  ms: number;
}> = ({ count, active, ms }) => {
  return (
    <div className="carousel-progress">
      {new Array(count).fill(null).map((n, idx) => (
        <div className="item" key={`progress_item_${idx}`}>
          <div
            className={`progress${idx === active ? " active" : ""}`}
            style={{ animationDuration: `${ms}ms` }}
          />
        </div>
      ))}
    </div>
  );
};

/**
 * 轮播图
 */
const Carousel: FC<ICarouselProps> = ({
  items,
  wrapperStyle,
  ms = DEFAULT_MS,
}) => {
  const activeIndex = useCarousel({
    items: items.map((i, idx) => idx),
    ms,
  });

  return (
    <div className="carousel" style={wrapperStyle}>
      <div
        className="carousel-content"
        style={{ transform: `translateX(${-activeIndex * 100}%)` }}
      >
        {items.map((i, idx) => (
          <CarouselItem {...i} key={`carousel_item_${idx}`} />
        ))}
      </div>
      <CarouselProgress count={items.length} active={activeIndex} ms={ms} />
    </div>
  );
};

export default Carousel;
