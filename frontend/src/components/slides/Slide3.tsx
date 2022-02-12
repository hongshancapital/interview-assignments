import React from "react";

const Slide = () => {
  return (
    <div
      className="slideContent"
      style={{ background: "#f1f1f3", color: "#444" }}
    >
      <div className="slideContent__bg" style={{ top: 600 }}>
        <img src={require("../../assets/airpods.png")} />
      </div>
      <div className="slideContent__pragraph">
        <h3>Buy a Tablet or XPhone for college.</h3>
        <h3>Get arPods</h3>
      </div>
    </div>
  );
};

export default Slide;
