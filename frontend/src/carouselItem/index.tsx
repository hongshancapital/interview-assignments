import React, { FunctionComponent } from "react";
import "./index.css";
export interface ICarouselItem {
  style?: React.CSSProperties;
  title?: string[];
  desc?: string[];
}

const CarouselItem: FunctionComponent<ICarouselItem> = (props) => {
  const { title = [], style = {}, desc = [] } = props;
  return (
    <div className="item-container" style={style}>
      <div>
        <div className="item-title">
          {title.map((item, index) => (<div key={index}>{item}</div>))}
        </div>
        <div className="item-desc">
          {desc.map((item, index) => (<div key={index}>{item}</div>))}
        </div>
      </div>
    </div>
  );
}

export default CarouselItem;