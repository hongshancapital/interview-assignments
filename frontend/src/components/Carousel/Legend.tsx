/**
 * Legend under Slice
 */
import React from "react";
import "./index.css";

interface LegendProps {
  index: number;
  activeIndex: number;
  onLegendClick?: Function;
  duration?: number;
}
const Legend: React.FC<LegendProps> = ({
  index,
  activeIndex,
  onLegendClick,
  duration,
}) => {
  const isActive = activeIndex === index;
  return (
    <div className="legend-container">
        <div
          className={`legend-wrap ${
            isActive ? "active" : ""
          }`}
          key={index}
          onClick={() => onLegendClick?.(index)}
        >
          <div
            className="legend-inner"
            style={{
              animationDuration: `${duration}s`,
            }}
          >
          </div>
        </div>
      </div>
  );
};

export default Legend;
