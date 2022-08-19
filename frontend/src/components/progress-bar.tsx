import React, { useEffect, useState } from "react";
import './progress-bar.css';

export function ProgressBar({speed, active}:{speed:number, active:boolean}){
  const [value, setValue] =useState(0);
  useEffect(() => {
    setValue(()=>active?100:0);
  },[active]);
  return(
    <div className="progress-wrapper">
      <div className="progress" style={{width:`${value}%`, transition: `${active?speed:0}ms linear`}}/>
    </div>
  )
}

export default function ProgressBars({speed, length, activeIndex}:{speed:number, length:number, activeIndex:number}){
  const data=new Array(length).fill(0);
  return (
    <div className="indicator-wrapper">
      {data.map((_,index)=><ProgressBar speed={speed} active={index===activeIndex} key={index}/>)}
    </div>
  )
}