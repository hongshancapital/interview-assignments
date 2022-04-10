import React from "react";
import "./App.css";
import airpods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import Carousel from './components/carousel'
function App() {
  let data = [
    {
      img: iphone,
      title: 'xPhone',
      subTitle: ['Lots to love.Less to spend.', 'Staring ar $399.'],
      color:'#ffffff'
    }, {
      img: tablet,
      title: 'Tablet',
      subTitle: 'Just the right amount of everything.',
      color:'#000000'
    }, {
      img: airpods,
      title: ['Buy a Tablet or xPhone for college.', 'Get arPords.'],
      color:'#000000'
    },]
  return <div className="App">
    <Carousel
      data={data}
    ></Carousel>
    </div>;
}

export default App;
