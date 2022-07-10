import React, { useState, useEffect } from "react";
import { useObserver } from "mobx-react";
import "./style.css";

const dotPositions = ["top", "bottom", "left", "right"];

export default function Carousel(props) {
    const {
        imgList,
        autoplay           //是否自动切换(默认true)
    } = props;

    //轮播列表索引(默认为0，第一个开始)
    const [activeNumber, setActiveNumber] = useState(0);

    //轮播时间间隔，默认为2秒
    const [times, setTimes] = useState(2000);

    //切换到指定面板
    const goTo = (slideNumber) => {
        if (slideNumber < 0) {
          setActiveNumber(0);
        } else if (imgList && slideNumber > (imgList.length - 1)) {
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
        if(autoplay){
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

    return useObserver(() => (
      <div className="my-carousel">
        <div
          className="slick-slider"
          style={{ backgroundColor: imgList[activeNumber].backColor }}
        >
          <div className="slick-list">
            {imgList &&
              imgList.length > 0 &&
              imgList.map((item, index) => {
                return (
                  <div
                    className={`slick-slide ${
                      activeNumber === index ? "slick-active" : ""
                    }`}
                    key={index}
                  >
                    <div className="slick-text">
                      <div
                        className="slick-title"
                        style={{ color: item.fontColor }}
                        dangerouslySetInnerHTML={{ __html: item.titile }}
                      ></div>
                      <div
                        className="slick-describe"
                        style={{ color: item.fontColor }}
                        dangerouslySetInnerHTML={{ __html: item.describe }}
                      ></div>
                    </div>

                    <img
                      src={item.src}
                      style={{
                        objectFit: item.fit,
                        width: item.width,
                        height: item.height,
                      }}
                    ></img>
                  </div>
                );
              })}
          </div>

          <ul
            className={`slick-dots slick-dots-bottom`}
            style={{ width: imgList.length * 36 + "px" }}
          >
            { imgList &&
              imgList.length > 0 &&
              imgList.map((item, index) => {
                return (
                  <li
                    className={
                      activeNumber === index ? "slick-active" : "#A09FA0"
                    }
                    style={{
                      backgroundColor: item?.butBackColor
                        ? item.butBackColor
                        : "",
                    }}
                    key={index}
                  >
                    <button onClick={() => goTo(index)}>{index}</button>
                  </li>
                );
              })}
          </ul>
        </div>
      </div>
    ));
}
