import React, { CSSProperties, FC, useMemo } from "react";
import "./index.css";

export interface IndicatorProps_Inter {
  active: boolean;
  period?: number;
}

const Indicator: FC<IndicatorProps_Inter> = ({ active, period = 3000 }) => {
  const barStyle: CSSProperties = useMemo(
    () => ({
      backgroundColor: active ? "#eaeaea" : "transparent",
      position: "absolute",
      top: 0,
      right: active ? 0 : "100%",
      transition: `right ${period}ms linear`,
    }),
    [active, period]
  );
  return (
    <div role="listitem" className="indicator-control">
      <div
        data-testid="indicator-bar"
        className="indicator-bar"
        style={barStyle}
      ></div>
    </div>
  );
};

export default Indicator;
