import React, { CSSProperties, FunctionComponent } from "react";
import "./index.css";
import { useCarousel } from '../hooks'
export interface ICarouselProps {
  duration?: number;
  style?: CSSProperties;

}


const Carousel: FunctionComponent<ICarouselProps> = (props) => {
  const {
    duration = 2000,
    children,
    style = {},
  } = props;
  const items = React.Children.toArray(children).filter(v => !!v);
  const { activeIndex, handleClick } = useCarousel(items.length, duration)
  return (
    <div className="carousel" style={style}>
      <div
        className="carousel-content"
        style={{
          transform: `translateX(-${activeIndex * 100}%)`,
        }}
      >
        {items.map((item, index) => (
          <div key={index} className="carousel-item">
            {item}
          </div>))}
      </div>
      <div className="carousel-indicators">
        {items.map((_, index) => {
          return (
            <div
              className={`carousel-dot ${index === activeIndex ? "active" : ""
                }`}
              style={{ animationDuration: duration + "ms" }}
              key={index}
              onClick={() => handleClick(index)}
            />
          );
        })}
      </div>
    </div>
  );
}


export default Carousel