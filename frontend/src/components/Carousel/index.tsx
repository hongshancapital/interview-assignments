import React from "react";
import useCarousel from "./useCarousel";
import {
  CarouselItemProps,
  CarouselProps,
  CarouselProgressProps,
} from "./interface";
import "./index.scss";

const DEFAULT_SLIDE_INTERVAL = 3000;
const DEFAULT_IMG_HEIGHT = "50%";

const CarouselItem: React.FC<CarouselItemProps> = ({
  src,
  alt,
  title,
  desc,
  imgHeight,
  wrapperStyle,
  titleStyle,
  descStyle,
}) => {
  return (
    <div className="carousel-item" style={wrapperStyle}>
      <div className="content">
        <div className="title" style={titleStyle}>
          {title.map((c, idx) => (
            <div key={`carousel_title_${idx}`}>{c}</div>
          ))}
        </div>
        <div className="desc" style={descStyle}>
          {desc?.map((c, idx) => (
            <div key={`carousel_desc_${idx}`}>{c}</div>
          ))}
        </div>
      </div>
      <img src={src} alt={alt} style={{ height: imgHeight }} />
    </div>
  );
};

const CarouselProgress: React.FC<CarouselProgressProps> = ({
  count,
  active,
  slideInterval,
}) => {
  return (
    <div className="carousel-progress">
      {new Array(count).fill(null).map((n, idx) => (
        <div className="item" key={`progress_item_${idx}`}>
          <div
            className={`progress ${idx === active ? " active" : ""}`}
            style={{ animationDuration: `${slideInterval}ms` }}
          />
        </div>
      ))}
    </div>
  );
};

const Carousel: React.FC<CarouselProps> = ({
  items,
  imgHeight = DEFAULT_IMG_HEIGHT,
  slideInterval = DEFAULT_SLIDE_INTERVAL,
  wrapperStyle,
}) => {
  const activeIndex = useCarousel({
    itemsLength: items.length,
    slideInterval,
  });

  return (
    <div className="carousel">
      <div
        className="carousel-content"
        style={{ transform: `translateX(${-activeIndex * 100}%)` }}
      >
        {items.map((config, idx) => (
          <CarouselItem
            imgHeight={imgHeight}
            {...config}
            key={`carousel_item_${idx}`}
          />
        ))}
      </div>
      <CarouselProgress
        count={items.length}
        active={activeIndex}
        slideInterval={slideInterval}
      />
    </div>
  );
};

export default Carousel;
