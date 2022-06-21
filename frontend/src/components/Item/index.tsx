import React from "react";
import "./index.css";

type Props = {
  data: {
    title?: string;
    subTitle?: string;
    image?: string;
    color?: string;
  };
};

export const Item = ({ data }: Props) => {
  const { title, subTitle, image, color } = data;

  return (
    <div className="item">
      <img src={data.image}></img>
      <div className="item-text" style={{ color }}>
        {title && <h1>{title}</h1>}
        {subTitle && <h2>{subTitle}</h2>}
      </div>
    </div>
  );
};
