import { nanoid } from "nanoid";
import React from "react";
import "./index.scss";

export interface ICarouselItem {
  image?: string;
  titleList?: string[];
  descList?: string[];
  backgroundColor?: string;
  textColor?: string;
}

interface ICarouselItemProps {
  data?: ICarouselItem;
}

const CarouselItem = (props: ICarouselItemProps) => {
  const { data = {} } = props;
  const {
    titleList = [],
    descList = [],
    image = "",
    backgroundColor = "#000",
    textColor = "#fff",
  } = data;
  return (
    <div
      className="comCarouselItem"
      style={{
        backgroundColor,
        color: textColor,
        backgroundImage: `url(${image})`,
      }}
    >
      <div className="textBox">
        {titleList.map((title) => {
          return (
            <div className="title" key={nanoid()}>
              {title}
            </div>
          );
        })}
        <div className="divider" />
        {descList.map((desc) => {
          return (
            <div className="desc" key={nanoid()}>
              {desc}
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default CarouselItem;
