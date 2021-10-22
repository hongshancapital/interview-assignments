import React, { ReactElement } from "react";
import useCarousel from "../../hooks/useCarousel";
import './carousel.css';

function Carousel({
  items,
  duration: propDuration = 3000
}: {
  items: Array<{ content: ReactElement, bgCover: string }>,
  duration?: number,
}) {
  const {
    activeIndex,
    pass,
    duration,
    resume,
    pause,
    select,
  } = useCarousel(items, propDuration);

  return (
    <div className="carousel">
      <div className="carousel-items-wrapper" style={ { transform: `translate(${- activeIndex * 100}%, 0px)` }}>
        {
          items?.map((item, index) => (
            <div
              className="carousel-item"
              key={`c_i_${index}`}
              style={ { backgroundImage: `url(${item.bgCover})` }}
            >{ item.content }</div>
          ))
        }
      </div>
      <div className="carousel-anchors">
        {
          items?.map((item, index) => (
            <div
              className="carousel-item-anchor"
              key={`c_i_a_${index}`}
              role="button"
              onMouseOver={ () => { pause(index) } }
              onMouseOut={ () => { resume(index) } }
              onClick={ () => { select(index) } }
            >
              <div
                className="carousel-item-anchor-body"
                style={ { width: activeIndex === index ? `${ (pass / duration * 100).toFixed(2) }%` : '0%' } }
              ></div>
            </div>
          ))
        }
      </div>
    </div>
  )
}

export default Carousel;
