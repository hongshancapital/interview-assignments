import React from "react";
import { CarouselIProps } from "../../index";

import "./style.scss";

export default function Item(props: CarouselIProps) {
  const { title, desc, src, alt, color } = props;

  return (
    <div className="item-container">
      <img className="item-img" src={src} alt={alt ?? ""} />
      <div className="item-content" style={{ color }}>
        {title.map((title, index) => (
          <p key={"title" + index} className="item-content-title">
            {title}
          </p>
        ))}
        {desc?.map((des, index) => (
          <p key={"des" + index} className="item-content-desc">
            {des}
          </p>
        ))}
      </div>
    </div>
  );
}
