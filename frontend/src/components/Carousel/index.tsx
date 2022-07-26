import React, { CSSProperties, FC } from "react";
import { CarouselItem } from "./types";
import { formatList } from "./utils";
import { useSlide } from "./hooks";
import "./style.scss";

export interface CarouselProps {
  items: CarouselItem[];
  width?: CSSProperties["width"];
  height?: CSSProperties["height"];
  interval?: number;
  animationDuration?: number;
  onChange?: (item: CarouselItem, index: number) => void;
}

const Carousel: FC<CarouselProps> = (props) => {
  const {
    items,
    width = "100%",
    height = "100%",
    interval = 2000,
    animationDuration = 500,
    onChange,
  } = props;

  const [currentIndex, isFirstRender] = useSlide(
    items,
    interval,
    animationDuration,
    onChange
  );

  const renderItem = (item: CarouselItem, index: number) => {
    const { title, subTitle = [], image } = item;
    let left = `${(index - currentIndex) * 100}%`;

    return (
      <div
        className="carousel-item"
        style={{
          color: item.foreColor,
          backgroundColor: item.backColor,
          transition: `left ${animationDuration}ms linear`,
          left,
        }}
        key={index}
      >
        {renderTitle(formatList(title))}
        {renderSubTitle(formatList(subTitle))}
        {renderImage(image)}
      </div>
    );
  };

  const renderTitle = (titleList: string[]) => {
    return (
      <div className="title-wrapper">
        {titleList.map((title, index) => {
          return <div key={index}>{title}</div>;
        })}
      </div>
    );
  };

  const renderSubTitle = (subTitleList: string[]) => {
    return (
      <div className="subtitle-wrapper">
        {subTitleList.map((title, index) => {
          return <div key={index}>{title}</div>;
        })}
      </div>
    );
  };

  const renderImage = (image: string) => {
    return (
      <div className="image-wrapper">
        <img src={image} alt="" />
      </div>
    );
  };

  const renderIndicator = () => {
    return (
      <div className="carousel-indicator">
        {items.map((_, index) => {
          const active = index === currentIndex;

          return (
            <div key={index} className="carousel-indicator-item">
              <div
                className="carousel-indicator-item-handler"
                style={{
                  width: isFirstRender || !active ? "0%" : "100%",
                  transition: active
                    ? `width ${interval + animationDuration}ms linear`
                    : "",
                }}
              />
            </div>
          );
        })}
      </div>
    );
  };

  return (
    <div className="carousel-wrapper" style={{ width, height }}>
      {items.map(renderItem)}
      {renderIndicator()}
    </div>
  );
};

export default Carousel;
