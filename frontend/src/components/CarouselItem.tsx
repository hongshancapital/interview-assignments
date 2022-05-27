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
      className="carousel-item"
      style={{
        color: textColor,
        backgroundImage: `url(${bgSrc})`,
      }}
    >
      <div className="title" dangerouslySetInnerHTML={{ __html: title }}></div>
      <div className="description text">{description}</div>
      <div className="price text">{price}</div>
    </div>
  );
};

export default CarouselItem;
