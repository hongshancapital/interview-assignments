import React, { useEffect, useState } from 'react'
import './App.css';
import { Carousel } from './Carousel'
import airpods from './assets/airpods.png'
import iphone from './assets/iphone.png'
import tablet from './assets/tablet.png'

function App() {
  return <div className='App'>
    <CarouselDemo/>
  </div>;
}

const CarouselStyle = { width: 900, height: 500}
function CarouselDemo () {
  return (
    <Carousel style={CarouselStyle} autoPlay>
      <div className='carousel-item carousel-item-black' style={{ background: '#111'}}>
        <div className='carousel-item-bg' style={{backgroundImage: `url(${iphone})`}}></div>
        <h3 className='carousel-item-title'>xPhone</h3>
        <p className='carousel-item-desc'>Lots to love.Less to spend.<br/>Starting at $399.</p>
      </div>
      <div className='carousel-item' style={{ background: '#fafafa'}}>
        <div className='carousel-item-bg' style={{backgroundImage: `url(${tablet})`,width: 1050, bottom: -20 }}></div>
        <h3 className='carousel-item-title'>Tablet</h3>
        <p className='carousel-item-desc'>Just the right amount of everything.</p>
      </div>
      <div className='carousel-item' style={{ background: '#f1f1f3'}}>
        <div className='carousel-item-bg' style={{backgroundImage: `url(${airpods})`, width: 1200, bottom: -40 }}></div>
        <h3 className='carousel-item-title'>Buy a Tablet or a xPhone for college.</h3>
        <p className='carousel-item-desc carousel-item-desc-large'>Get arPods.</p>
      </div>
    </Carousel>
  )
}

export default App;
