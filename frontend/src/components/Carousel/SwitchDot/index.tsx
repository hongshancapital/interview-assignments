import React, { FC } from "react";
import { ISwitchProps } from "../CommonTypes";
import "./index.css";

export const SwitchDot: FC<ISwitchProps> = (props) => {
  const { curIndex, dotNum } = props;
  let dots = [];
  for (let i = 0; i < dotNum; i++) {
    dots.push(
      <div
        onClick={() => {
          props.onChange && props.onChange(i);
        }}
        key={i}
        className={i === curIndex ? "dot active" : "dot"}
      >
        <span className="dot-progress"></span>
      </div>
    );
  }
  return <div className="dot--box">{dots}</div>;
};
