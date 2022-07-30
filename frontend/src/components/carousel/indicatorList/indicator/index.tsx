import React, { CSSProperties, FC } from "react";
import { PERIOD } from "../../config";
import "./index.css";

export interface IndicatorProps_Inter {
  active: boolean;
}

const Indicator: FC<IndicatorProps_Inter> = ({ active = false }) => {
  
  const barStyle: CSSProperties = {
    backgroundColor:  active ? "#eaeaea" : "transparent",
    position: "absolute",
    top:0,
    right: active ? 0 : "100%",
    transition: `right ${PERIOD}ms linear`,
  }
  return <div className="indicator-control"><div className="indicator-bar" style={barStyle}></div></div>;
};

export default Indicator;
