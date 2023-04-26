import React, { useCallback, useEffect, useState } from "react";

interface CarouselHandleProps {
  length: number;
  speed: number;
  current: number;
}

const CarouselHandle: React.FC<CarouselHandleProps> = (props) => {
  const { length, speed, current } = props;

  const [currentActive, setCurrentActive] = useState(-1);
  useEffect(() => { 
    setCurrentActive(current);
  }, [current])

  return (
    <div className="carousel-handle">
      {Array.apply(null, Array(length)).map((i, index) => {
        let isActive: boolean = currentActive === index;
        return (
          <div key={index} className="carousel-handle-item">
            <div
              className={`carousel-handle-item-lt ${isActive ? "active" : ""}`}
              style={{ transitionDuration: `${speed / 1000}s` }}
            ></div>
          </div>
        );
      })}
    </div>
  );
};

export default CarouselHandle;
