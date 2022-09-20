import React from "react";
import "./App.css";
import Carousel from './Carousel'
import imgUrl1 from './assets/airpods1.png';
import imgUrl2 from './assets/iphone1.png';
import imgUrl3 from './assets/tablet1.png';

type Data = Array<{
  backgroundImg: string,
  backgroundColor: string,
  title: string,
  describe?: string,
  fontColor: string,
}>

function App() {
  const arr: Data = [
    {
      backgroundImg: imgUrl2,
      backgroundColor: '#111111',
      title: 'xPhone',
      describe: 'Lots to love. Less to spend. Starting at $399',
      fontColor: '#fff',
    },{
    backgroundImg: imgUrl1,
    backgroundColor: '#F1F1F3',
    title: 'Buy a Tablet  or xPhone for college Get airPods',
    fontColor: '#000',
  },{
    backgroundImg: imgUrl3,
    backgroundColor: '#FAFAFA',
    title: 'Tablet',
    describe: 'just the right amount of everything',
    fontColor: '#000',
  }]
  return <div className="App">
    <Carousel data={arr} />
  </div>;
}

export default App;
