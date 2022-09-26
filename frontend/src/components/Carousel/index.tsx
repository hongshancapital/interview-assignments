import React, { useEffect, useState } from "react";
import "./index.scss";

interface DataInformation {
  imageUrl: string;
  title: string;
  subTitle?: string;
  fontColor: string;
}

interface CarouselProps {
  dataInfo: DataInformation[];
  duration: number;
  width?: string;
  height?: string;
  onClick?: (event: React.MouseEvent<HTMLElement>) => void;
}

function Carousel(props: CarouselProps) {
  const { dataInfo, duration, width, height } = props;
  const [distance, setDistance] = useState(0);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [sliderCurIndex, setSlider] = useState(0);

  useEffect(() => {
    function handleMove(currentIndex: number) {
      const distance = currentIndex * 100;
      setDistance(distance);
      if (currentIndex === dataInfo.length - 1) {
        setCurrentIndex(0);
      } else setCurrentIndex(currentIndex + 1);
    }
    const timer = setInterval(() => {
      handleMove(currentIndex);
    }, duration);
    return () => {
      clearInterval(timer);
    };
  }, [currentIndex, dataInfo.length, duration]);

  useEffect(() => {
    const timer = setTimeout(() => {
      setSlider(currentIndex);
    }, duration);
    return () => {
      clearTimeout(timer);
    };
  }, [currentIndex, duration]);

  return (
    <div
      className="carousel"
      style={{ width: `${width}`, height: `${height}` }}
    >
      <div className="carousel-wrap" style={{ left: `${-distance}%` }}>
        {dataInfo.map((item, index) => {
          return (
            <div
              className="carouse-item"
              key={"carousel" + index}
              style={{
                backgroundImage: `url(${item.imageUrl})`,
                color: `${item.fontColor}`,
              }}
            >
              <div className="carouse-item-content">
                <div className="carouse-item-title">{item.title}</div>
                <div className="carouse-item-subtitle">{item.subTitle}</div>
              </div>
            </div>
          );
        })}
      </div>
      <div className="slider">
        {dataInfo.map((item, index) => {
          return (
            <div
              key={"slider" + index}
              className={`slider-item ${
                sliderCurIndex >= index && "active-slider-item"
              }`}
            />
          );
        })}
      </div>
    </div>
  );
}

export default Carousel;
