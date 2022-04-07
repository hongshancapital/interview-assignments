import React, { FC, CSSProperties } from "react";
import useCarousel from "./useCarousel";
import "./index.css";

interface ICarouselItemProps {
  src: string;
  alt?: string;
  title: string[];
  desc?: string[];
  /**
   * 单独为一项配置图片区域高度
   */
  overlayImgHeight?: ICarouselProps["imgHeight"];
  wrapperStyle?: CSSProperties;
  titleStyle?: CSSProperties;
  descStyle?: CSSProperties;
}

export interface ICarouselProps {
  items: ICarouselItemProps[];
  /**
   * 设置图片区域高度，图片会居中展示，溢出部分 hidden（PS：文字区域自适应剩余高度）
   */
  imgHeight: CSSProperties["height"];
  /**
   * 轮播间隔，单位 ms
   * @default 3000
   */
  ms?: number;
  wrapperStyle?: CSSProperties;
}

const DEFAULT_MS = 3000;

/**
 * 轮播图项
 */
const CarouselItem: FC<ICarouselItemProps> = ({
  src,
  alt,
  title,
  desc,
  overlayImgHeight,
  wrapperStyle,
  titleStyle,
  descStyle,
}) => {
  return (
    <div className="carousel-item" style={wrapperStyle}>
      <div className="content">
        <div className="title" style={titleStyle}>
          {title.map((c, idx) => (
            <div key={`title_${idx}`}>{c}</div>
          ))}
        </div>
        <div className="desc" style={descStyle}>
          {desc?.map((c, idx) => (
            <div key={`desc_${idx}`}>{c}</div>
          ))}
        </div>
      </div>
      <img src={src} alt={alt} style={{ height: overlayImgHeight }} />
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
  imgHeight,
  ms = DEFAULT_MS,
  wrapperStyle,
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
        {items.map((config, idx) => (
          <CarouselItem
            overlayImgHeight={imgHeight}
            {...config}
            key={`carousel_item_${idx}`}
          />
        ))}
      </div>
      <CarouselProgress count={items.length} active={activeIndex} ms={ms} />
    </div>
  );
};

export default Carousel;
