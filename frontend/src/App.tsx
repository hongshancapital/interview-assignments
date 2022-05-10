import React from "react";
import "./App.css";
import { Carousel } from "./components/Carousel";
import RENDERDATA from './const';

// Carousel props 示例查看 RENDERDATA
interface CarouselProps {
  data: {
    bgColor: 'string'; // 背景色
    textColor: 'string'; // 文案颜色
    title: 'string'|'string'[]; // 标题
    subtitle?: 'string'|'string'[]; // 子标题
    imgUrl: 'string'; // 图片路径
  }[],
  width?: string; // 组件宽度 默认800px
  height?: string // 组件高度 默认600px
}

function App() {
  return <div className="App">
    <Carousel data={RENDERDATA} />
  </div>;
}

export default App;
