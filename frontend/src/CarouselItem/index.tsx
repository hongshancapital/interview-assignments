import React from "react";
import "./CarouselItem.css";
export type CarouseItemProps = {
  title: string;
  desc?: string;
  color: string;
  image: string;
};
const Item: React.FC<CarouseItemProps> = ({ title, desc, color, image }) => {
  return (
    <div
      className="item-wrapper"
      style={{
        backgroundImage: `url(${image})`,
        color: color,
      }}
    >
      <div className="text-wrapper">
        <div className="title">{title}</div>
        <div className="desc">{desc}</div>
      </div>
    </div>
  );
};
export default Item;
