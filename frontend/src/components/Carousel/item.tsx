import React from "react";

export interface ItemProps {
  id: string;
  title: string;
  sub: string;
  image: string;
  color: string;
}

const CarouselItem: React.FC<ItemProps> = (props: ItemProps) => {
  return (
    <div className="carousel-item">
      <div className="title" style={{ color: `${props.color}` }}>
        <div dangerouslySetInnerHTML={{ __html: props.title }} />
        <div className="sub" dangerouslySetInnerHTML={{ __html: props.sub }} />
      </div>
      <img src={props.image}></img>
    </div>
  );
};

export default CarouselItem;
