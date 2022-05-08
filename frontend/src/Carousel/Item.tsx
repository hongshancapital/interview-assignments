import React from "react";
import { DataItem } from "../types";

const PREFIX = "carousel-item";

interface CarouselItemProps {
  data: DataItem;
}

function CarouselItem({ data }: CarouselItemProps) {
  const style = {
    color: data.color,
    backgroundColor: data.backgroundColor,
  };

  return (
    <div className={PREFIX} style={style}>
      <div>
        {data.title.map((item: string) => (
          <div key={item} className={`${PREFIX}-title`}>
            {item}
          </div>
        ))}
        {data.subTitle?.map((item: string) => (
          <div key={item} className={`${PREFIX}-subTitle`}>
            {item}
          </div>
        ))}
      </div>
      <div className={`${PREFIX}-img`}>
        <img src={data.img} alt={data.title[0]} />
      </div>
    </div>
  );
}

export default CarouselItem;
