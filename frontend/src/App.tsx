/**
* @decsription 入口文件
* @author xiaoshihui 
* @Date 2022-07-19
*/

import React from "react";
import Carousel from './components/carousel'
import "./App.css";
import xphone from "./assets/iphone.png";
import tablet from "./assets/tablet.png";
import airpod from "./assets/airpods.png";

function App() {
  /** 内容可配置 */
  const config = [
    {
      key: 0,
      title: ['xPhone'],
      subTitle: ['Lots to love.Less to speed.', 'Starting at $399'],
      image: xphone,
      backgroundColor: '#111111',
      color: '#fff',
    },
    {
      key: 1,
      title: ['Tablet'],
      subTitle: ['Just the right amount of everything'],
      image: tablet,
      backgroundColor: '#fafafa',
    },
    {
      key: 2,
      title: ['Buy a tablet or xPhone for college.', 'Get arPods'],
      subTitle: [],
      image: airpod,
      backgroundColor: '#f1f1f3',
    },
  ]

  return (
    <div className="App">
      <Carousel config={config} />
    </div>
  )
}
export default App;