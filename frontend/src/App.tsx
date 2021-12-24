import React from "react";
import "./App.css";
import Carousel from "./Carousel";
/**
 * Carousel组件说明
 * width {Number} - 外层容器宽度
 * height {Number} - 外层容器高度
 * delay {Number} - 每次轮播的时间
 * speed {Number} - 每次轮播的速度
 * 
 * 基础版的轮播，自动轮播。子元素(slide) 需要加上 className='slide'
 * 可自定义个数，目前超过三个的slide都使用了第三个的背景图。
 * 
 */
function App() {
  return <div className="App">
      <Carousel
        width={700}
        height={400}
        delay={3000}
        speed={500}
      >
        <div key={1} className='slide'>
          <h3>
            Buy a Tablet or xPhone for college.
            <br />
            Get airPods.
          </h3>
        </div>
        <div key={2} className='slide'>
          <h3>xPhone</h3>
          <p>
            Lots to love. Less to spend.
            <br />
            Starting at $399
          </p>
        </div>
        <div key={3} className='slide'>
          <h3>Tablet</h3>
          <p>
            Just the right amount of everything
          </p>
        </div>
      </Carousel>
  </div>;
}

export default App;
