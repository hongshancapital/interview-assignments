import React from "react";
import { ProgressBarType } from "../types";
import { useProgressCount } from "../hooks";

const ProgressBar = ({ 
  isCurrent, 
  time,
  ...args
} : ProgressBarType) => {
  const progressCount = useProgressCount(time, isCurrent); 

  return (
    <div className="nav-bar" {...args}>
      <div 
        className="nav-progress" 
        data-testid="nav-progress" 
        style={{width: `${Math.round(progressCount * 100)}%`}}
       >
      </div>
    </div>
  );
}

export default ProgressBar;
