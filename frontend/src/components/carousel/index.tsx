import React, { useCallback, useEffect, useRef, useState } from "react";
import { CarouselDataType } from "../../type";
import "./index.css";

interface CarouselProps {
  carouselData: CarouselDataType[];
}

const Carousel: React.FC<CarouselProps> = ({ carouselData }) => {
  const [size, setSize] = useState({
    width: 0,
    height: 0,
  });
  const [currentIndex, setCurrentIndex] = useState(0);
  const ref = useRef(0);

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
    ref.current = window.setInterval(() => {
      setCurrentIndex((i) => (i === carouselData.length - 1 ? 0 : i + 1));
    }, 3000);
  }, [carouselData.length]);

  useEffect(() => {
    timeInterval();
    return () => window.clearInterval(ref.current);
  }, [timeInterval]);

  const handleClick = (index: number) => {
    window.clearInterval(ref.current);
    setCurrentIndex(index);
    timeInterval();
  };

  return (
    <div
      className="container"
      id="carousel"
      style={{
        width: `${size.width}px`,
        height: `${size.height}px`,
      }}
    >
      <div
        className="content"
        style={{
          width: `${size.width * carouselData.length}px`,
          transform: `translateX(${currentIndex * -size.width}px)`,
        }}
      >
        {carouselData.map((item) => (
          <div
            className="item"
            style={{
              backgroundImage: `url(${item.backgroundImg})`,
              width: `${size.width}px`,
            }}
            key={item.id}
          >
            <div className="textContent">
              {item.title.map((current, index) => (
                <h1 key={`${current}-${index}`} style={{ color: item.color }}>
                  {current}
                </h1>
              ))}
              {item.description?.map((current, index) => (
                <h2 key={`${current}-${index}`} style={{ color: item.color }}>
                  {current}
                </h2>
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

export default Carousel;
