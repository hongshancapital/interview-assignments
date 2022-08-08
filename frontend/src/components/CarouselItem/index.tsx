import React from "react";
import { ICarouselItem } from "../../types/carousel";
import "./index.scss";

// 轮播图 item
export default function CarouserItem(props: { child: ICarouselItem }) {
  let { color = "black", title = "", context = [], img } = props.child;

  return (
    <div className="carouser__item" style={{ color }}>
      <img
        className="carouser__item__img"
        src={require(`../../assets/${img}`)}
        alt={title}
      />
      <div className="carouser__item__container">
        <div className="carouser__item__title">{title}</div>
        <div className="carouser__item__content">
          {(context || []).map((v, i) => {
            return <div key={i}>{v}</div>;
          })}
        </div>
      </div>
    </div>
  );
}
