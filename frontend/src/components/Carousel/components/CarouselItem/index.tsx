import { nanoid } from "nanoid";
import React from "react";
import { ICarouselItemProps } from "./type";
import "./index.scss";

const defaultStyle = {
  backgroundImage: "",
  backgroundColor: "#000",
  color: "#fff",
};

const CarouselItem = (props: ICarouselItemProps) => {
  const { data = {} } = props;
  const { titleList = [], descList = [], style = defaultStyle } = data;
  return (
    <div className="comCarouselItem" style={style}>
      <div className="textBox">
        {titleList.map((title) => {
          return (
            <div className="title" key={nanoid()}>
              {title}
            </div>
          );
        })}
        {Boolean(titleList.length && descList.length) && (
          <div className="divider" />
        )}
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
