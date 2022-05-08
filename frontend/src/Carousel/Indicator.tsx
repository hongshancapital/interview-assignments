import React from "react";

const PREFIX = "carousel-indicator";

interface IndicatorProps {
  activeIndex: number;
  count: number;
  onChange: (index: number) => void;
}

function Indicator({ activeIndex, count, onChange }: IndicatorProps) {
  const arr = Array.from({ length: count });

  return (
    <div className={PREFIX}>
      {arr.map((_, index) => (
        <div
          key={index}
          className={`${PREFIX}-dot ${
            activeIndex === index && `${PREFIX}-dot--active`
          }`}
          onClick={() => onChange(index)}
        >
          <div className="progress"></div>
        </div>
      ))}
    </div>
  );
}

export default Indicator;
