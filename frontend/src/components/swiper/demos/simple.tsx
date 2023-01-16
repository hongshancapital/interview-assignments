import React from "react";
import Swiper from "../index";
import "./simple.css";

import airpods from "./../../../assets/airpods.png"
import iphone from "./../../../assets/iphone.png"
import tablet from "./../../../assets/tablet.png"

const Simple: React.FC = () => {
  return (
    <div>
      <Swiper autoplay={true}  className="simple-swiper">
        <div className="simple-swiper__item simple-swiper__item-iphone">
          <div className="simple-swiper-item__header">
            <p className="simple-swiper__title">xPhone</p>
            <p>Lots to love. Less to spend.</p>
            <p>Starting at $399.</p>
          </div>
          <img src={iphone} alt="" />
        </div>
        <div className="simple-swiper__item simple-swiper__item-tablet">
          <div className="simple-swiper-item__header">
            <p className="simple-swiper__title">Tablet</p>
            <p>Just the right amount of everything.</p>
          </div>
          <img src={tablet} alt="" />
        </div>
        <div className="simple-swiper__item simple-swiper__item-airpods">
          <div className="simple-swiper-item__header">
            <p className="simple-swiper__title">Buy a Tablet or xPhone for college.</p>
            <p className="simple-swiper__title">Get arPods.</p>
          </div>
          <img src={airpods} alt="" />
        </div>
      </Swiper>
    </div>
  );
};

export default Simple;
