/*
 * @Author: 'xing.feng' '243279223@qq.com'
 * @Date: 2022-07-10 09:53:33
 * @LastEditors: 'xing.feng' '243279223@qq.com'
 * @LastEditTime: 2022-07-10 19:03:30
 * @FilePath: \frontend\src\App.tsx
 * @Description: 
 * 
 * Copyright (c) 2022 by 'xing.feng' '243279223@qq.com', All Rights Reserved. 
 */
import React from "react";
import Carousel from './components/Carousel'
import "./App.css";
import xphone from "./assets/iphone.png";
import tablet from "./assets/tablet.png";
import airpod from "./assets/airpods.png";

function App() {
  const data = [
    {
      title: ['xPhone'],
      subTitle: ['Lots to love.Less to speed.', 'Starting at $399'],
      backgroundImage: xphone,
      backgroundColor: '#111111',
      color: '#fff',
    },
    {
      title: ['Tablet'],
      subTitle: ['Just the right amount of everything'],
      backgroundImage: tablet,
      backgroundColor: '#fafafa',
    },
    {
      title: ['Buy a tablet or xPhone for college.', 'Get arPods'],
      subTitle: [],
      backgroundImage: airpod,
      backgroundColor: '#f1f1f3',
    },
  ]

  return <div className="App">{
    <Carousel data={data}></Carousel>
  }</div>;
}

export default App;
