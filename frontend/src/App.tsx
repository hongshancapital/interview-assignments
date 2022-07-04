import React from "react";
import Carousel from './components/carousel';
import airpods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import "./App.css";

const carouselList = [
  {
    img: iphone,
    text: [
      {label: 'xPhone',fontSize: '30px',color:'#fff'},
      {label: 'Lots to love.Less to spend',fontSize: '22px',color:'#fff'},
      {label: 'Starting at $399.',fontSize: '22px',color:'#fff'},
    ]
  },
  {
    img: tablet,
    text: [
      {label: 'Tablet',fontSize: '30px',color:'#000'},
      {label: 'Just the tight amount of everything',fontSize: '30px',color:'#000'},
    ]
  },
  {
    img: airpods, 
    text: [
      {label: 'But a Tablet or xPhone for college.',fontSize: '30px',color:'#000'},
      {label: 'Get arpods.',fontSize: '22px',color:'#000'},
    ]
  }
]

function App() {
  return (
    <div className="App">
      <Carousel 
        carouselList={carouselList}
        speed={3000}
      />
    </div>
  );
}

export default App;