import React, { FC, useMemo } from "react";
import { merge } from "../utils/merge";
import "./index.css";

export interface IIndicatorProps {
  verticalAlign?: "top" | "center" | "bottom";
  horizontalAlign?: "top" | "center" | "bottom";
}

const defaultProps: IIndicatorProps = {
  verticalAlign: "bottom",
  horizontalAlign: "center",
};

export const Indicator: FC<IIndicatorProps> = ({ horizontalAlign, verticalAlign }) => {
  const props = useMemo<IIndicatorProps>(
    () => merge(defaultProps, { verticalAlign, horizontalAlign }),
    [verticalAlign, horizontalAlign]
  );

  return <div className="indicator"></div>;
};
