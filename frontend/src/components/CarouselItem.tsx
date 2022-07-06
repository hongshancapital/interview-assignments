
import React from "react";
import { carousel } from "./../type"
function CarouselItem(props: carousel) {
  return <li className="carousel-item" style={{ background: `${props.bgColor}` }}>
    <div className="carousel-item-des">
      <h1 className="carousel-item-title" style={{ color: `${props.fontColor}` }}>{props.title}</h1>
      <p className="carousel-item-text" style={{ color: `${props.fontColor}` }}>{props.textDes}</p>
    </div>
    <img className="carousel-item-img" src={props.imgSrc} alt="" />
  </li>
}
export default CarouselItem;
