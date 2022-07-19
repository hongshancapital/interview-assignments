import React, { useEffect, useState } from "react";
import "./App.css";

interface CarouselProps {
  renderPicObj: {
    number: number;
    url: string;
    title: string;
    desc?: string;
    others?: string;
    color: string;
    bgColor: string;
  }[];
}

function App(props: CarouselProps) {
  const { renderPicObj } = props;
  const [activeNumer, set_activeNumer] = useState(1);
  const [activeWidth, set_activeWidth] = useState(0);

  // 一个定时器控制 两个开关的切换
  useEffect(() => {
    if (activeWidth === 120) {
      set_activeWidth(0);
      if (activeNumer < renderPicObj.length) {
        set_activeNumer(activeNumer + 1); // 小于renderPicObj.length时向右推进
      } else {
        set_activeNumer(1); // 等于renderPicObj.length时，则回到1
      }
    } else {
      setTimeout(() => {
        set_activeWidth(activeWidth + 1);
      }, 25);
    }
  }, [activeWidth, activeNumer, renderPicObj.length]);

  return (
    <div className="App">
      {renderPicObj.map((item) => {
        return (
          <span
            style={{
              background: item.bgColor,
              color: item.color,
              transform: `translateX(-${(activeNumer - 1) * 100}%)`,
            }}
            className="textChunkStyle"
            key={item.number}
          >
            {/* 标题 */}
            <div className="title">{item.title}</div>
            {/* 描述 */}
            <div className="text">{item.desc}</div>
            {/* 图片 */}
            <img src={item.url} alt="buy" className="img" />
          </span>
        );
      })}
      {/* 进度条 */}
      <div className="progressBarChunk">
        <span className="progressBar"></span>
        <span className="progressBar"></span>
        <span className="progressBar"></span>
        <span
          style={{
            width: activeWidth,
            left: (activeNumer - 1) * 125, // (item的宽度+边距)
          }}
          className="active"
        ></span>
      </div>
    </div>
  );
}

export default React.memo(App);
