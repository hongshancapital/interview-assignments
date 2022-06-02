import React from "react";
import "./index.css";

type indicatorProps = {
  indicatorData: {
    active: boolean;
    duration: number;
    adjust: number;
  },
}

function Indicator(props: indicatorProps) {
  const { active, duration, adjust } = props.indicatorData;
  return <div className="indicator">
    {
      active ? <div className="indicator-progress"
        style={{
          animation: (adjust ? 'changeProgressFirst ' : 'changeProgress ') + duration + 'ms linear',
        }}>
      </div> : null
    }
  </div>;
}

export default Indicator;
