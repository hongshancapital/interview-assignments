import React from "react";
import "./App.css";

import airpods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';

import Carousel,{DataSourceType} from './components/Carousel';

const carouselDataSource:DataSourceType=[
  {
    title: ['xPhone'],
    desc: ['Lots to love.Less to spend.', 'Starting at $399.'],
    color: '#fff',
    bgc: '#111111',
    bgi: iphone,
  },
  {
    title: ['Tablet'],
    desc: ['Just the right amount of everything'],
    bgc: '#FAFAFA',
    bgi: tablet,
  },
  {
    title: ['Buy a Tablet or xPhone for college.', 'Get arPods.'],
    bgc: '#F1F1F3',
    bgi: airpods,
  },
]

function App() {
  return (
    <div className="App">
      <Carousel dataSource={carouselDataSource} duration={3000} />
    </div>
  );
}

export default App;
