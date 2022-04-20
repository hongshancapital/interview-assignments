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
        data={[{
          img: iphone,
          title: ['xPhone'],
          desc: ['Lots to love.Less to spend.', 'Staring ar $399.'],
          color: '#fff'
        }, {
          img: tablet,
          title: ['Tablet'],
          desc: ['Just the right amount of everything.'],
          color: '#000'
        }, {
          img: airpods,
          title: ['Buy a Tablet or xPhone for college.', 'Get arPords.'],
          color: '#000'
        }]}
      >
      </Carousel>
    </div>
  );
}

export default App;
