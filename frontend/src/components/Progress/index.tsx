import React, { useEffect, useState } from "react";
import "./styles.css";
function Progress({
  duration = 3000,
  width = 50,
  data,
  index = 0,
}: ProgressProps) {
  const [color, setColor] = useState(data[index].color || "#000");
  useEffect(() => {
    setColor(data[index].color || "#000");
  }, [index]);
  return (
    <div className="progress">
      {data.map((d, i) => {
        return (
          <div
            className={"progress-item" + (i === index ? " on" : "")}
            key={i}
            style={{ width: width }}
          >
            <div
              className="progress-back"
              style={{ backgroundColor: color }}
            ></div>
            <div
              className="progress-indicator"
              style={{
                backgroundColor: color,
                animationDuration: (duration / 1000).toFixed(0) + "s",
              }}
            ></div>
          </div>
        );
      })}
    </div>
  );
}
export default Progress;
