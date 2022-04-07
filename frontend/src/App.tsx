import React from "react";
import "./App.css";
import GoodsCard, { GoodsCardProps } from "./components/GoodsCard";
import Carousel from './components/Carousel'
import iphone from './assets/iphone.png'
import tablet from './assets/tablet.png'
import airpods from './assets/airpods.png'

const goodsList: GoodsCardProps[] = [{
  headline: 'xPhone',
  subhead: 'Lots to love. Less to spend.\nStarting at $399',
  image: iphone,
  backgroundColor: "#111",
  color: "white",
}, {
  headline: 'Tablet',
  subhead: 'Just the right amount of everything.',
  image: tablet,
  backgroundColor: "#FAFAFA",
  color: "black",
},{
  headline: 'Bug a Tablet or xPhone for college.\nGet arPods',
  image: airpods,
  backgroundColor: "#F1F1F3",
  color: "black",
}]

function App() {
  return <div className="App">
    <Carousel auotPlay>{
      goodsList.map(item => <GoodsCard key={item.headline} {...item} />)
    }</Carousel>
  </div>
}

export default App;
