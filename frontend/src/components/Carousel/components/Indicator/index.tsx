import React from "react";
import "./index.css";
export interface IndecatorProps {
  length: number;
  delay: number;
  index: number;
}
export const Indecator = (props: IndecatorProps) => {
  const { index: currentItemIndex, length, delay } = props;
  const itemList = Array.from({ length })
    .fill(0)
    .map((_, index) => index);

  return (
    <div className="indecator-wrapper">
      {itemList.map((item) => (
        <div key={item} className={"item"}>
          <div
            style={{
              transition: item === currentItemIndex ? `${delay}ms` : "0ms",
              width: item === currentItemIndex ? "100%" : "0%",
            }}
            className="inner-color"
          ></div>
        </div>
      ))}
    </div>
  );
};

export default Indecator;
