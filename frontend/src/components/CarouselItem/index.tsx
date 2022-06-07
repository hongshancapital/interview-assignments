import { nanoid } from "nanoid";
import React, { CSSProperties } from "react";
import "./index.scss";

export interface ICarouselItem {
  titleList?: string[];
  descList?: string[];
  style?: CSSProperties;
}

interface ICarouselItemProps {
  data?: ICarouselItem;
}

const CarouselItem = (props: ICarouselItemProps) => {
  const { data = {} } = props;
  const { titleList = [], descList = [], style = {} } = data;
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
