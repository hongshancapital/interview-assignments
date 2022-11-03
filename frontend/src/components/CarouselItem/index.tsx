import React from "react";
import "./styles.css";

function CarouselItem({
  title,
  desc,
  pic,
  background,
  color = "#000",
}: CarouselItem) {
  const renderTitle = () => {
    return typeof title === "string"
      ? title
      : title.map((t, i) => <div key={i}>{t}</div>);
  };
  const renderDesc = () => {
    return typeof desc === "string"
      ? desc
      : desc?.map((d, i) => <div key={i}>{d}</div>);
  };
  return (
    <div
      className="carousel-item"
      style={{
        backgroundColor: background,
        color,
        backgroundImage: `url(${pic})`,
      }}
    >
      <div className="carousel-item-wrap">
        <div className="title">{renderTitle()}</div>
        <div className="text">{renderDesc()}</div>
      </div>
    </div>
  );
}

export default CarouselItem;
