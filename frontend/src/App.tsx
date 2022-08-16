import React, { useState, useEffect } from "react";
import "./App.css";
import pic1 from './assets/iphone.png';
import pic2 from './assets/tablet.png';
import pic3 from './assets/airpods.png';

function Carousel() {
  const [curIndex, setCurIndex] = useState(0);// 当前显示图片的次序
  const [switching, setSwitching] = useState(false);// 启动标志，用于处理启动前的特殊状态
  const interval: number = 3000; // 图片切换时间
  const picCount: number = 3;// 图片数量
  let counter: number = 0; // 用于循环切图自增计数

  useEffect(() => {
    startSwitch();
  }, [])

  function startSwitch() {
    switchPic();// 立即启动切换
    setInterval(() => {
      switchPic()
    }, interval);
  }

  function switchPic() {// 切换图片
    setSwitching(true);
    const index = (counter++) % picCount;
    setCurIndex(index);
  }

  function renderNav() {// 导航栏部分
    let process = new Array(picCount).fill(0);

    return <div className="nav-layer">
      <div className="nav-bar">
        {process.map((p, index) =>
          <div className="outer" key={index}>
            <div className="inner" style={switching && index === curIndex ? {
              width: `100%`,
              transition: `width ${interval}ms linear`,
            } : {
              width: `0%`,
            }} />
          </div>
        )}
      </div>
    </div>
  }

  function renderPics() {// 轮播图主体部分
    const offset = 800;
    let position = curIndex;
    
    return <div className="pic-box">
      <div className="slice dark" style={{ left: offset * (0 - position) }}>
        <div className="title">xPhone</div>
        <div className="text">Lots to love. Less to spend. <br />Starting at $399.</div>
        <img className="pic" src={pic1} alt="" key={1} />
      </div>
      <div className="slice" style={{ left: offset * (1 - position) }}>
        <div className="title">Tablet</div>
        <div className="text">Just the right amount of everything.</div>
        <img className="pic" src={pic2} alt="" key={2} />
      </div>
      <div className="slice" style={{ left: offset * (2 - position) }}>
        <div className="title">Buy a Tablet or xPhone for college. <br />Get arpods</div>
        <img className="pic" src={pic3} alt="" key={3} />
      </div>
      {renderNav()}
    </div>
  }
  return <div className="App">{renderPics()}</div>;
}

export default Carousel;
