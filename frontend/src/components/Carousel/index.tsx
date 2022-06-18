import React, { ReactElement, useState } from "react";
import "./index.scss";
import ProgressBar from "../ProgressBar";

type Props = {
  children: ReactElement[];
  duration: number;
};

function Carousel(props: Props) {
  const { children, duration } = props;

  const [indexShowing, setIndexShowing] = useState(0);

  const handleProgressEnd = () => {
    if (indexShowing === children.length - 1) {
      setIndexShowing(0);
    } else {
      setIndexShowing(indexShowing + 1);
    }
  };

  return (
    <div className="carousel">
      <div
        className="slides"
        style={{ transform: `translateX(-${indexShowing * 100}%)` }}
      >
        {children.map((c) => {
          return c;
        })}
      </div>
      <div className="propress-bars">
        {children.map((_, i) => {
          return (
            <ProgressBar
              duration={duration}
              run={i === indexShowing}
              width={"40px"}
              height={"2px"}
              borderRadius={"1px"}
              onProgressEnd={handleProgressEnd}
            />
          );
        })}
      </div>
    </div>
  );
}

export default Carousel;
