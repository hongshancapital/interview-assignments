import React from "react";
import { ProgressBarType } from "../types";
import { useProgressCount } from "../hooks";

const ProgressBar = ({ 
  isCurrent, 
  time, 
} : ProgressBarType) => {
  const progressCount = useProgressCount(time); 
  // console.log(processCount);
  return (
    <div className="nav-bar">
      <div className="nav-process" style={isCurrent ? {width: `${progressCount * 100}%`} : {}}></div>
    </div>
  );
}

export default ProgressBar;
