import React from "react";
import Carousel from './carousel';
import airpods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import "./App.css";

function App() {
  return (
    <div className="App">
      <Carousel
        datas={[{
          title: ['xPhone'],
          desc: ['Lots to love.Less to spend.', 'Staring ar $399.'],
          img: iphone,
          color: '#fff'
        }, {
          title: ['Tablet'],
          desc: ['Just the right amount of everything.'],
          img: tablet,
          color: '#000'
        }, {
          title: ['Buy a Tablet or xPhone for college.', 'Get arPords.'],
          img: airpods,
          color: '#000'
        }]}
        duration={3000}>
      </Carousel>
    </div>
  );
}

export default App;
