import React from "react";
import "./indicator.css";

interface Props {
  count: number;
  index: number;
  progress: number;
  style?: React.CSSProperties;
  theme: "light" | "dark";
}
export const Indicator = ({ count, index, progress, style, theme }: Props) => {
  return (
    <div className="indicator-container" style={style}>
      {[...Array(count)].map((_, i) => {
        const width = i === index ? `${progress * 100}%` : "0%";

        let backgroundColor = "rgba(255, 255, 255, 0.2)";
        let foregroundColor = "rgb(255, 255, 255)";

        if (theme === "light") {
          backgroundColor = "rgba(0, 0, 0, 0.1)";
          foregroundColor = "rgb(0, 0, 0)";
        }

        return (
          <div
            key={i}
            className="indicator-bar"
            style={{ backgroundColor: backgroundColor }}
          >
            <div
              className="progress-bar"
              style={{ width, backgroundColor: foregroundColor }}
            ></div>
          </div>
        );
      })}
    </div>
  );
};
