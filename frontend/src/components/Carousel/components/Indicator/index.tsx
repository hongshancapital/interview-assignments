import React from "react";

import "./index.css";

interface IndicatorClick {
  (index: number): void;
}

interface IndicatorProps {
  length: number;
  delay: number;
  currentIndex: number;
  onClick: IndicatorClick;
}

export default function Indicator(props: IndicatorProps) {
  const { currentIndex, length, delay, onClick } = props;
  const indicatorList = new Array(length).fill(0).map((_, index) => index);

  return (
    <div className="indicator-wrapper">
      {
        indicatorList.map(item => (
        <div key={item} className="indicator" onClick={() => onClick(item)}>
          <div
            className="inner-color"
            style={{
              transition: item === currentIndex ? `${delay}ms` : "0ms",
              width: item === currentIndex ? "100%" : "0%",
            }}
          ></div>
        </div>
      ))
      }
    </div>
  );
}
