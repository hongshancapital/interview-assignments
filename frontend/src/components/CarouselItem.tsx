import React, { ReactElement } from "react";

type ItemProps = {
  bgSrc: string;
  textColor: string;
  title: string;
  description?: string;
  price?: string;
};

const CarouselItem = (props: ItemProps): ReactElement => {
  const { bgSrc, textColor, title, description, price } = props;

  return (
    <div
      style={{
        display: "inline-block",
        width: "100vw",
        height: "100%",
        color: textColor,
        backgroundImage: `url(${bgSrc})`,
        overflow: "hidden",
        backgroundSize: "cover",
        backgroundPosition: "center -300px",
      }}
    >
      <div className="title" dangerouslySetInnerHTML={{ __html: title }}></div>
      <div className="description text">{description}</div>
      <div className="price text">{price}</div>
    </div>
  );
};

export default CarouselItem;
