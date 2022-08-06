import React from 'react';
import { CarouselInfo } from '../../interface/carousel';
import './index.scss';

function CarouselItem(props: { carouselInfo: CarouselInfo }) {
  const { title, subTitle, img, color } = props.carouselInfo;
  let style: React.CSSProperties = {
    backgroundImage: `url(${img})`,
    color,
  };
  return (
    <div className="carousel-item" style={style}>
      <div className="carousel-title">
        {title.map((txt: string) => {
          return <p key={txt}>{txt}</p>;
        })}
      </div>
      <div className="carousel-sub-title">
        {subTitle &&
          subTitle.map((txt: string) => {
            return <p key={txt}>{txt}</p>;
          })}
      </div>
    </div>
  );
}

export default CarouselItem;
