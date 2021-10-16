import React from "react";
import "./App.css";
import Swiper from './components/Swiper'

function App() {
  const imgs:Array<string> = [
    require('./assets/iphone.png'),
    require('./assets/tablet.png'),
    require('./assets/airpods.png'),
  ]
  const time:number = 3000
  return <div className="App">
    <Swiper img_urls={imgs} time={time}></Swiper>
  </div>;
}

export default App;
