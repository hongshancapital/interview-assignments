import React from "react";
import "./index.css";
import { PropsType } from "../../types/index"

function SwiperItem(props: PropsType) {
  console.log(props)
  const { children } = props
  return <div className="swiper-item">
    {children}</div>;
}

export default SwiperItem;
