import React from "react";

import "./style.scss";

export interface CarouselIProps {
  id: string;
  src: string;
  title: string[];
  desc?: string[];
  alt?: string;
  color?: string;
}

export default function Item(props: CarouselIProps) {
  const { title, desc, src, alt, color } = props;

  return (
    <div className="item-container">
      <img className="item-img" src={src} alt={alt ?? ""} />
      <div className="item-content" style={{ color }}>
        {title.map((title: string, index: number) => (
          <p key={"2" + index} className="item-content-title">
            {title}
          </p>
        ))}
        {desc?.map((des: string, index: number) => (
          <p key={"1" + index} className="item-content-desc">
            {des}
          </p>
        ))}
      </div>
    </div>
  );
}
