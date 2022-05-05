import React from "react";
import "./App.css";
import Carousel from "./components/Carousel/index";

function App() {
  // 这里的测试数据直接通过require引入，实际使用过程中可能直接使用cdn图片完整地址
  const datas = [
    {title: 'xPhone', subTitle: 'Lots to love.Less to spend.Starting at $399.', color: '#fff', bg: require('@/assets/iphone.png')},
    {title: 'Tablet', subTitle: 'Just the right amount of everything.', bg: require('@/assets/tablet.png')},
    {title: 'Buy a Tablet or xPhone for college.Get arPods.', subTitle: '', bg: require('@/assets/airpods.png')}
  ];
  return (<div className="App" 
  style={{
    height: '500px'
  }}
  >
    {<Carousel datas={datas} />}
  </div>);
}

export default App;
