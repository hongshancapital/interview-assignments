import React, { useState, useEffect } from "react";
import { useObserver } from "mobx-react";

import "./style.css";

const dotPositions = ["top", "bottom", "left", "right"];

export default function Carousel(props) {
  const {
    imgList = [],
    autoplay = true, //是否自动切换(默认true)
  } = props;

  //轮播列表索引(默认为0，第一个开始)
  const [activeNumber, setActiveNumber] = useState(0);

  //轮播时间间隔，默认为2秒
  const [times, setTimes] = useState(2000);

  const imgLen = useState(imgList && imgList.length ? imgList.length : 0);
  //切换到指定面板
  const goTo = (slideNumber) => {
    if (slideNumber < 0) {
      setActiveNumber(0);
    } else if (imgList && slideNumber > imgList.length - 1) {
      setActiveNumber(0);
    } else {
      setActiveNumber(slideNumber);
    }
  };

  //切换到下一面板
  const next = () => {
    setActiveNumber(activeNumber + 1);
  };

  //切换到上一面板
  const prev = () => {
    setActiveNumber(activeNumber - 1);
  };

  useEffect(() => {
    if (autoplay) {
      const interval = setInterval(() => {
        goTo(activeNumber + 1);
      }, times);
      return () => {
        if (interval) {
          clearInterval(interval);
        }
      };
    }
  });

  //加载图片和描述内容
  const imgLisCom = () => {
    if (imgList && imgList.length > 0) {
      return imgList.map((item, index) => {
        const {
          titile,
          describe,
          fit = "cover",
          width = "100%",
          height = "100%",
        } = item;
        
        return (
          <div
            className={`slick-slide ${
              activeNumber === index && "slick-active"
            }`}
            key={index}
          >
            <div className="slick-text">
              <div className="slick-title" style={{ color: item.fontColor }}>
                {titile}
              </div>
              <div className="slick-describe" style={{ color: item.fontColor }}>
                {describe}
              </div>
            </div>

            <img
              src={item.src}
              style={{
                objectFit: fit,
                width: width,
                height: height,
              }}
              alt={titile}
            ></img>
          </div>
        );
      });
    } else {
      return <></>;
    }
  };

  //加载底部dots
  const slickDotsCom = () => {
    if (imgList && imgList.length > 0) {
      return imgList.map((item, index) => {
        const { butBackColor = '' } = item;

        return (
          <li
            className={`${
              activeNumber === index && "slick-active"
            }`}
            style={{ backgroundColor: butBackColor }}
            key={index}
          >
            <button onClick={() => goTo(index)}>{index}</button>
          </li>
        );
      });
    } else {
      return <></>;
    }
  };

  return useObserver(() => (
    <div className="my-carousel">
      <div className="slick-slider" style={{ backgroundColor: imgList[activeNumber]?.backColor }} >
        <div className="slick-list">{ imgLisCom() }</div>

        <ul className={`slick-dots slick-dots-bottom`} style={{ width: `{ ${imgLen * 36}}px` }} >
          {slickDotsCom()}
        </ul>
      </div>
    </div>
  ));
}
