import React from "react";
import "./App.css";
import Carousel from "./Carousel/Carousel";

function App() {
  // 1、图片向左轮播，从最后一张到第一张，动画还原。
  // 2、轮播条,是进度条。
  // 3、暴露的属性，图片如果一致，可以再抽出轮播元素数组。
  // 4、ts写法
  // 5、组件的模块拆分
  return <div className="App">{/* write your component here */}
  {/* name='helloTypeScript' age={25} */}
  <Carousel  />
  </div>;
}

export default App;
