import React, { useState, useEffect } from "react";
import Progress from "./progress";

interface DotsProps {
  children?: React.ReactNode;
  delay: number;
  activeKey?: number;
}

const Dots: React.FC<DotsProps> = ({ children, delay, activeKey }): React.ReactElement => {
  return (
    <div className="dots-list">
      {React.Children.map(children, (child, i) => {
        return (
          <div className={activeKey == i ? "dots-item dots-active" : "dots-item"} key={i}>
            {activeKey == i && <Progress delay={delay} />}
          </div>
        );
      })}
    </div>
  );
};

export default Dots;
