import React from "react";
import "./index.scss";

interface ITimeBarItemProps {
  active?: boolean;
  stayTime?: number;
}

const TimeBarItem = (props: ITimeBarItemProps) => {
  const { active = false, stayTime = 2 } = props;
  return (
    <div className="comTimeBarItem">
      {active && (
        <div
          className="progress"
          style={{
            animationDuration: `${stayTime}s`,
          }}
        />
      )}
    </div>
  );
};

export default TimeBarItem;
