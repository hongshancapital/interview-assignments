import React from "react";
import "./Carousel.css";
import { useSlider } from "./hooks";
const Carousel: React.FC<{ children: React.ReactNode[] }> = ({ children }) => {
  const count = React.Children.count(children);
  const index = useSlider(count);
  return (
    <div className="wrapper">
      <div
        className="list-wrapper"
        style={{
          width: `${100 * count}%`,
          transform: `translateX(-${(100 * index) / count}%)`,
        }}
      >
        {children.map((child, idx) => {
          return (
            <div className="list-item" key={idx}>
              {child}
            </div>
          );
        })}
      </div>

      <div className="dots-wrapper">
        {children.map((child, idx) => {
          return (
            <div className="dot-outer" key={idx}>
              <div
                className="dot-inner"
                style={{ width: `${idx === index ? "100%" : 0}` }}
              ></div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default Carousel;
