import React from "react";
import useCarousel from "../../hooks/useCarousel";
import CreateElements from "../CreateElements/CreateElements";
import { CarouselList } from '../../@types'
import './carousel.css';

function Carousel({
  sourceList,
  duration: propDuration = 3000
}: {
  sourceList: CarouselList,
  duration?: number,
}) {
  const {
    activeIndex,
    pass,
    duration,
  } = useCarousel(sourceList, propDuration);

  return (
    <div className="carousel">
      <div className="carousel-items_wrapper" style={ { transform: `translate(${- activeIndex * 100}%, 0px)` }}>
        {
          sourceList?.map((item, index) => (
            <div
              className="carousel-item"
              key={`j_f_${index}`}
              style={ { backgroundImage: `url(${item.bgUrl})` }}
            >{
              item?.data.map((json)=>{
                return CreateElements(json)
              })
            }</div>
          ))
        }
      </div>
      <div className="carousel-anchors">
        {
          sourceList?.map((item, index) => (
            <div
              className="carousel-item_anchor"
              key={`j_f_${index}`}
              role="button"
            >
              <div
                className="carousel-item_anchorbody"
                style={{ width: activeIndex === index ? `${(pass / duration * 100).toFixed(2)}%` : '0%' }}
              ></div>
            </div>
          ))
        }
      </div>
    </div>
  )
}

export default Carousel;
