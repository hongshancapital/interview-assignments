import React from "react";
import "./PageItem.css";
import { CarouselItem } from "./types";

type Prop = CarouselItem;
function PageItem({
  iconType,
  fontColor = "#000000",
  backgroundColor = "#ffffff",
  title,
  text,
}: Prop) {
  return (
    <div className="page-item" style={{ backgroundColor, color: fontColor }}>
      <div className="page-flex-item" />
      <div>
        <div className="title">
          {title?.map((item) => (
            <div key={item}>{item}</div>
          ))}
        </div>
        <div className="text">
          {text?.map((item) => (
            <div key={item}>{item}</div>
          ))}
        </div>
        <div className="page-flex-item" />
        <div>
          <div className={`imageWrapper ${iconType}`}></div>
        </div>
      </div>
    </div>
  );
}

export default PageItem;
