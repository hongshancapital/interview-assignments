import React, { FC } from "react";

import Swiper, { SwiperProps } from "../Swiper";
import CarouselItem, { CarouselItemProps } from "./CarouselItem";

interface Props {
  list: CarouselItemProps[];
}

const Carousel: FC<Props> = ({ list }) => {
  return (
    <Swiper>
      {list.map((data, index) => {
        return <CarouselItem {...data} key={index} />;
      })}
    </Swiper>
  );
};

export default Carousel;
