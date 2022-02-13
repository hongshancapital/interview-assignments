import React from "react";

const Slide = () => {
  return (
    <div
      className="slideContent"
      style={{ background: "#fafafa", color: "black" }}
    >
      <div className="slideContent__bg" style={{ top: "-4rem" }}>
        <img src={require("../../assets/tablet.png")} />
      </div>
      <div className="slideContent__pragraph">
        <h3>Tablet</h3>
        <p>Just the right amount of everything</p>
      </div>
    </div>
  );
};

export default Slide;
