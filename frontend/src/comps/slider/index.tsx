import React, { ReactElement, useState } from "react";
import "./index.css";

function Slider(props: { content: ReactElement; len: number }) {
  const [currentIndex, setCurrentIndex] = useState<number>(0);

  const handleTabAnimateEnd = (index: number) => {
    setCurrentIndex((index + 1) % props.len);
  };

  return (
    <div className="slider">
      <div className="parts">
        <div className="container" style={{ left: `${-100 * currentIndex}%` }}>
          {props.content}
        </div>
      </div>
      <div className="tabs">
        {new Array(props.len).fill(0).map((d, i) => {
          return (
            <div
              key={i}
              className={"tab" + (currentIndex === i ? " animate" : "")}
              onAnimationEnd={(e) => handleTabAnimateEnd(i)}
            ></div>
          );
        })}
      </div>
    </div>
  );
}

export default Slider;
