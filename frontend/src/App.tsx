import React from 'react'

import Carousel from './components/Carousel'

import './App.css'

function App () {
  return <div className="App" style={{
    width: '100vw',
    height: '100vh',
    margin: 'auto',
    backgroundColor: '#333'
  }}>
    <Carousel
      autoplayTime={2000}
    >
      <div className="item" style={{ backgroundColor: '#111', color: '#fff'}}>
        <img className="item-img" src="assets/iphone.png" alt="img"/>
        <div className="item-desc">
          <h2 className="item-title">xPhone</h2>
          <p>Lots to love. Less to spend.</p>
          <p>Starting at $399</p>
        </div>
      </div>
      <div className="item" style={{ backgroundColor: '#fafafa', color: '#000'}}>
        <img className="item-img" src="assets/tablet.png" alt="img"/>
        <div className="item-desc">
          <h2 className="item-title">Tablet</h2>
          <p>Just the right amount of everything.</p>
        </div>
      </div>
      <div className="item" style={{ backgroundColor: '#f1f1f3', color: '#000'}}>
        <img className="item-img" src="assets/airpods.png" alt="img"/>
        <div className="item-desc">
          <h2 className="item-title">Buy a Tablet or xPhone for college.</h2>
          <h2 className="item-title">Get airPods.</h2>
        </div>
      </div>
    </Carousel>
  </div>
}

export default App
