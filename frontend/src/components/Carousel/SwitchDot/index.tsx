import React, { FC } from "react";
import { createImgArr } from "../../../utils/tools";
import { ISwitchProps } from "../CommonTypes";
import "./index.css";

export const SwitchDot: FC<ISwitchProps> = (props) => {
  const { imgSrcs,curIndex} = props;
  const dotArr = createImgArr(imgSrcs);
  const dots = dotArr.map((d,i)=>(
      <div
        onClick={() => {
          props.onChange && props.onChange(i);
        }}
        key={d.id}
        className={i === curIndex ? "dot active" : "dot"}
      >
        <span className="dot-progress"></span>
      </div>
  ));
  return <div className="dot--box">{dots}</div>;
};
