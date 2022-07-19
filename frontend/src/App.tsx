import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";
import {ICarouselItem} from "./components/Carousel/data";

// 轮播图集合
const carouselItems:ICarouselItem[] = [{
  title: 'xPhone',
  subTitle: 'Lots to love. Less to spend. Starting at $399.',
  pic: require('./assets/iphone.png'),
  className: 'ci-title-wrap-0'
}, {
  title: 'Tablet',
  subTitle: 'Just the right amount of everything.',
  pic: require('./assets/tablet.png')
}, {
  title: 'Buy a Tablet or xPhone for college.',
  subTitle: 'Get arPods.',
  pic: require('./assets/airpods.png'),
  className: 'ci-title-wrap-2'
}]

function App() {
  return <div className="App">
    <Carousel items={carouselItems} duration={3000} speed={400}/>
  </div>;
}

export default App;
