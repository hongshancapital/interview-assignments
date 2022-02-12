import React from "react";

const Slide = () => {
  return (
    <div
      className="slideContent"
      style={{ background: "#111", color: "white" }}
    >
      <div className="slideContent__bg" style={{ top: -120 }}>
        <img src={require("../../assets/iphone.png")} />
      </div>
      <div className="slideContent__pragraph" style={{ top: 200 }}>
        <h3>xPhone</h3>
        <p>Lots to love. Less to spend.</p>
        <p>Starting at $399.</p>
      </div>
    </div>
  );
};

export default Slide;
