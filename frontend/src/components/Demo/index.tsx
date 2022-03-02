import React from "react";
import iphoneImg from "../../assets/iphone.png";
import tabletImg from "../../assets/tablet.png";
import airpodsImg from "../../assets/airpods.png";
import "./index.css";

export const IphoneImg = () => {
  return (
    <div className="demo demo-iphone">
      <img src={iphoneImg} alt="iphone" />
      <div>轮播图</div>
    </div>
  );
};

export const TabletImg = () => {
  return (
    <div className="demo demo-tablet">
      <img src={tabletImg} alt="iphone" />
      <div>轮播图</div>
    </div>
  );
};

export const AirpodsImg = () => {
  return (
    <div className="demo demo-airpods">
      <img src={airpodsImg} alt="iphone" />
      <div>轮播图</div>
    </div>
  );
};
