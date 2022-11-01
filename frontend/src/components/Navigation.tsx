import * as React from "react";
import { useCarouselNavigate } from "./Carousel";

const Navigation = () => {
  const { goto, totalPaper, currentPaper } = useCarouselNavigate();

  return (
    <div className="navigation">
      {Array.from(Array(totalPaper)).map((_i, index) => (
        <div className="bar" key={index} onClick={() => goto(index)}>
          <div
            className={`bar-progress ${
              currentPaper === index ? "in-progress" : ""
            }`}
          />
        </div>
      ))}
    </div>
  );
};

export default Navigation;
