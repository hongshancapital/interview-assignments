import React from "react";
import Carsouel from './components/carousel';
import "./App.css";
import imgIphone from './assets/iphone.png';
import imgTablet from './assets/tablet.png';
import imgAirpods from './assets/airpods.png';

function App() {

  const srcs = [
    {
      src: imgIphone,
      id: 'iphone',
      title: 'xPhone',
      content: 'Lots to love. Less to spend. \nStarting at $399.',
      theme: 'black',
      backgroundColor: '#111111'
    },
    {
      src: imgTablet,
      id: 'tablet',
      title: 'Tablet',
      content: 'Just the right amount of everything.',
      backgroundColor: '#fafafa'
    },
    {
      src: imgAirpods,
      id: 'airpods',
      title: 'Buy a Tablet or xPhone for college.\nGet airPods.',
      content: '',
      backgroundColor: '#f1f1f3'
    },
  ]

  return (
    <div className="App w-per-100 h-per-100">
      <Carsouel
        srcs={srcs}
        delay={0.5}
        dur={3}
      ></Carsouel>
    </div>
  );
}

export default App;
