import React, { useRef } from 'react';
import useCarouselIndex from '../../hooks/useCarouselIndex';

import './index.css';

interface Item {
  titles: string[];
  contents?: string[];
  fontColor: string;
  backgroundImage: string;
  backgroundColor: string;
}

type CarouselProps = {
  /**
   * 每一页幻灯片的内容
   */
  items: Item[];
  /**
   * 幻灯片播放的时间（单位毫秒），默认值 2500
   */
  duration?: number;
};

const defaultDuration = 2500;
export default function Carousel(props: CarouselProps): JSX.Element {
  const { duration = defaultDuration, items } = props;

  const containerRef = useRef<HTMLDivElement>(null);

  const carouselIndex = useCarouselIndex(
    () => containerRef.current?.children || [],
    duration,
  );

  return (
    <div className="carousel">
      <div className="carousel-items-container" ref={containerRef}>
        {items.map((item, index) => (
          <CarouselItem key={index} item={item} />
        ))}
      </div>

      <div className="carousel-indicator">
        {items.map((item, index) => (
          <div
            className={`carousel-indicator-line ${
              carouselIndex === index ? 'active' : ''
            }`}
            style={{ animationDuration: `${duration}ms` }}
            key={index}
          ></div>
        ))}
      </div>
    </div>
  );
}

function CarouselItem(props: { item: Item }): JSX.Element {
  const { backgroundColor, fontColor, backgroundImage, titles, contents } =
    props.item;

  return (
    <div
      className="carousel-item"
      style={{ backgroundColor, color: fontColor }}
    >
      <div>
        {titles.map((title) => (
          <div className="carousel-title" key={title}>
            {title}
          </div>
        ))}
        {contents &&
          contents.map((content) => (
            <div className="carousel-content" key={content}>
              {content}
            </div>
          ))}
      </div>

      <div className="carousel-icon">
        <img src={backgroundImage} alt="icon" />
      </div>
    </div>
  );
}
