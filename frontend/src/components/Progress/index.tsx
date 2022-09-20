import React from "react";
import "./index.css";

interface Props {
  count: number;
  activeIndex: number;
  ms: number;
}

const Index = ({ count, activeIndex, ms }: Props) => {
  return (
    <div className="progress_wrapper">
      {new Array(count).fill(0).map((n, idx) => (
        <div className="item" key={idx}>
          {idx === activeIndex && <div className="progress_active" />}
        </div>
      ))}
    </div>
  );
};

export default Index;
