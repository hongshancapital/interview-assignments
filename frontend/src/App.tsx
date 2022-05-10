import React from "react";
import { Carousel } from './components';
import iphoneImg from './assets/iphone.png';
import tabletImg from './assets/tablet.png';
import airpodsImg from './assets/airpods.png';
import "./App.css";

const dataSet = [
  {
    content: (
      <div style={{ background: `url(${iphoneImg}) no-repeat center center / contain fixed #111`, height: '100%' }}></div>
    ),
    key: '1'
  },
  {
    content: (
      <div style={{ background: `url(${tabletImg}) no-repeat center 60% / contain fixed #fafafa`, height: '100%' }}></div>
    ),
    key: '2'
  },
  {
    content: (
      <div style={{ background: `url(${airpodsImg}) no-repeat center 70% / contain fixed #f1f1f3`, height: '100%' }}></div>
    ),
    key: '3',
  }
];

function App() {
  return (
    <div className="App">
      <Carousel
        style={{ height: '100%' }}
        data={dataSet}
        frameDuration={2000}
        intervalDuration={1000}
      />
    </div>
  )
}

export default App;
