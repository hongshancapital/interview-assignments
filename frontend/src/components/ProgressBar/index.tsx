import React from "react";
import "./index.css";

interface IProgressBarProps {
  count: number;
  currentIndex: number;
}
const Index = (props: IProgressBarProps) => {
  const { count, currentIndex } = props;
  return (
    <div className="progressbar">
      {Array.from(Array(count).keys()).map((i, index) => (
        <div
          className={`${currentIndex === index && "progress-anima"} part`}
          key={index}
        ></div>
      ))}
    </div>
  );
};
export default Index;
