import './index.css';
import React from 'react';
import ReactDOM from 'react-dom/client';
// import MyCarousel from './App';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import airpods from './assets/airpods.png';
import Carousel from './App';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);

const items = [
  {
    id: 1,
    image:iphone,
    title: "iPhone",
    description:
      "The sun rises over the horizon, painting the sky with warm colors.",
    prices:'Starting at $399.'
  },
  {
    id: 2,
    image:tablet,
    title: "Tablet",
    description:
      "KKKKKK ises over the horizon, painting the sky with warm colors.",
    prices:'Starting at $499.'
  },
  {
    id: 3,
    image:airpods,
    title: "airpods",
    description:
      "KKKKKK ises over the horizon, painting the sky with warm colors.",
    prices:'Starting at $599.'
  }
]

root.render(
  <React.StrictMode>
    <Carousel items={items}/>
  </React.StrictMode>
);
