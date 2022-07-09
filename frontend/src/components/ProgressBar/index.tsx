import React from "react";
import "./index.css";

interface IProgressBarProps {
  len: number;
  currentItem: number;
}
const Index = (props: IProgressBarProps) => {
  const { len, currentItem } = props;
  return (
    <div className="progressbar">
      {Array.from(Array(len).keys()).map((i, index) => (
        <div
          className={`${currentItem === index && "progress-anima"} part`}
          key={index}
        ></div>
      ))}
    </div>
  );
};
export default Index;
