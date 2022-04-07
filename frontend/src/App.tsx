import React from "react";
import Carousel from "./components/carousel";
import "./App.css";
import airpods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';

function App() {
  return <div className="App">
    <Carousel
      data={[
        {
          backgroundColor: '#111111',
          color: '#fff',
          img: iphone,
          title: 'xPhone',
          desc: ['Lots to love.Less to spend.', 'Staring ar $399.'],
        }, {
          backgroundColor: '#FAFAFA',
          color: '#000',
          img: tablet,
          title: 'Tablet',
          desc: 'Just the right amount of everything.',
        }, {
          backgroundColor: '#F1F1F3',
          color: '#000',
          img: airpods,
          title: ['Buy a Tablet or xPhone for college.', 'Get arPords.'],
        },
      ]}
    />
  </div>;
}

export default App;
