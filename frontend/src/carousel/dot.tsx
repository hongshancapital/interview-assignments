import React from "react";

interface IDot{
  duration: number;
  currentIdx: number;
  idx: number;
}
const Dot = (
  {duration, currentIdx, idx}: IDot
  ) => {
  return <div className='dotContainer'>
    {currentIdx === idx && <div style={{animation: `process ${duration / 1000}s linear`, transition: `1s easy`}}></div>}
  </div>
}

export default Dot;
