import React, { useEffect, useState } from "react";
import ProgressBar from "../ProgressBar";

import "./index.css";

interface ContentStyle {
  color: string;
}
export interface ICarouselList {
  imgUrl: string;
  title: string[];
  text: string[];
  contentStyle?: ContentStyle;
}

interface ICarouselProps {
  carouselList?: ICarouselList[];
}

interface ICarouselItemProps {
  carouselItem: ICarouselList;
  currentIndex: number;
}

const CarouselItem = (props: ICarouselItemProps) => {
  const { carouselItem, currentIndex } = props;
  const { imgUrl, title, text, contentStyle } = carouselItem;
  const styleObj = {
    background: "url(" + imgUrl + ")",
    backgroundSize: "auto 100%",
    backgroundRepeat: "no-repeat",
    backgroundPositionX: "50%",
    left: `-${currentIndex * 100}%`,
    transition: "left 0.5s linear",
  };
  return (
    <div className="carousel" style={styleObj}>
      <div className="content" style={contentStyle}>
        {title.map((item) => (
          <div className="title" key={JSON.stringify(item)}>
            {item}
          </div>
        ))}
        {text.map((item) => (
          <div className="text" key={JSON.stringify(item)}>
            {item}
          </div>
        ))}
      </div>
    </div>
  );
};

const Index = (props: ICarouselProps) => {
  const { carouselList } = props;
  const carouselLen = carouselList?.length;
  const [currentTime, setCurrentTime] = useState(0);
  useEffect(() => {
    const timer = setTimeout(() => setCurrentTime(currentTime + 1), 3500);
    return () => clearTimeout(timer);
  }, [currentTime]);

  if (!carouselLen) return null;
  const currentIndex = currentTime % carouselLen;
  return (
    <>
      {carouselList.map((item: any) => (
        <CarouselItem
          carouselItem={item}
          currentIndex={currentIndex}
          key={JSON.stringify(item)}
        />
      ))}
      <ProgressBar
        count={carouselLen}
        currentIndex={currentIndex}
        key="progressbar"
      />
    </>
  );
};

export default Index;
