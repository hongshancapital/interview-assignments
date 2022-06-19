import React, { ReactElement, useEffect, useState } from "react";
import "./index.scss";
import ProgressBar from "../ProgressBar";

type Props = {
  children: ReactElement[];
  duration: number;
  startWithIndex?: number;
  progressBarWidth?: number;
  progressBarHeight?: number;
};

function Carousel(props: Props) {
  const {
    children,
    duration,
    startWithIndex = 0,
    progressBarWidth = 40,
    progressBarHeight = 2,
  } = props;

  const [indexShowing, setIndexShowing] = useState(0);

  const handleProgressEnd = () => {
    if (indexShowing === children.length - 1) {
      setIndexShowing(0);
    } else {
      setIndexShowing(indexShowing + 1);
    }
  };

  useEffect(() => {
    if (startWithIndex > children.length - 1 || startWithIndex < 0) {
      setIndexShowing(0);
    } else {
      setIndexShowing(startWithIndex);
    }
  }, [startWithIndex, children]);

  return (
    <div className="carousel">
      <div
        data-testid="slides"
        className="slides"
        style={{ transform: `translateX(-${indexShowing * 100}%)` }}
      >
        {children.map((c) => {
          return c;
        })}
      </div>
      <div className="propress-bars" data-testid="progress-bars">
        {children.map((_, i) => {
          return (
            <div key={_.key} style={{ padding: "0 3px" }}>
              <ProgressBar
                duration={duration}
                run={i === indexShowing}
                width={`${progressBarWidth}px`}
                height={`${progressBarHeight}px`}
                borderRadius={`${progressBarHeight / 2}px`}
                onProgressEnd={handleProgressEnd}
              />
            </div>
          );
        })}
      </div>
    </div>
  );
}

type SlideProps = {
  children: ReactElement;
};

export function Slide(props: SlideProps) {
  const { children } = props;

  return (
    <div
      data-testid="slide"
      style={{
        width: "100%",
        height: "100%",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        flexShrink: 0,
      }}
    >
      {children}
    </div>
  );
}

export default Carousel;
