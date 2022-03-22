import React, { FC, memo, useCallback, useEffect, useMemo } from "react";
import { CarouselDataType, ease, ValueOf } from "./type";
import "./index.css";
import { deepEqual } from "./utils";

export interface CarouselProps {
  carouselData: CarouselDataType[];
  easing?: ValueOf<typeof ease>;
}

const Carousel: FC<CarouselProps> = (props) => {
  const { carouselData, easing = ease.linear } = props;
  const [size, setSize] = React.useState({
    width: 0,
    height: 0,
  });
  const [currentIndex, setCurrentIndex] = React.useState(0);
  const ref = React.useRef(0);

  useEffect(() => {
    const parentElement = (document.getElementById("carousel") as HTMLElement)
      .parentElement as HTMLElement;
    const width = parentElement.clientWidth;
    const height = parentElement.clientHeight;
    setSize({
      width,
      height,
    });
  }, []);

  const timeInterval = useCallback(() => {
    window.clearInterval(ref.current);
    ref.current = window.setInterval(() => {
      setCurrentIndex((i) => (i === carouselData.length - 1 ? 0 : i + 1));
    }, 3000);
  }, [carouselData.length]);

  useEffect(() => {
    timeInterval();
    return () => window.clearInterval(ref.current);
  }, [timeInterval]);

  const handleClick = (index: number) => {
    setCurrentIndex(index);
    timeInterval();
  };

  const contentWidth = useMemo(
    () => size.width * carouselData.length,
    [carouselData.length, size.width]
  );

  return (
    <div
      id="carousel"
      style={{
        width: `${size.width}px`,
        height: `${size.height}px`,
        transitionTimingFunction: easing,
      }}
    >
      <div
        className="content"
        style={{
          width: `${contentWidth}px`,
          transform: `translateX(${currentIndex * -size.width}px)`,
        }}
      >
        {carouselData.map((item) => (
          <div
            className="item"
            style={{
              backgroundImage: `url(${item.backgroundImg})`,
              width: `${size.width}px`,
              color: item.color,
            }}
            key={item.id}
          >
            <div className="textContent">
              {item.title.map((current, index) => (
                <h1 key={`${current}-${index}`}>{current}</h1>
              ))}
              {item.description?.map((current, index) => (
                <h2 key={`${current}-${index}`}>{current}</h2>
              ))}
            </div>
          </div>
        ))}
      </div>
      <ul className="select">
        {carouselData.map((item, index) => (
          <li
            data-select={index === currentIndex}
            key={item.id}
            onClick={() => handleClick(index)}
          />
        ))}
      </ul>
    </div>
  );
};

export default memo(Carousel, deepEqual);
