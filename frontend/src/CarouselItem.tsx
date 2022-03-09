import React from "react";
import "./CarouselItem.css";

interface Props {
  children: React.ReactElement;
  imageUrl: string;
  itemStyle: React.CSSProperties;
}

export default function CarouselItem(props: Props) {
  const { children, imageUrl, itemStyle } = props;
  return (
    <div className="carousel-item-container" style={itemStyle}>
      {children}
      <div
        className="image"
        style={{
          backgroundImage: `url(${imageUrl})`,
        }}
      ></div>
    </div>
  );
}
