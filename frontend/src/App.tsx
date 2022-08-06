import React from 'react'
import './App.css'
import Carousel from './components/Carousel'
import iphone from './assets/iphone.png'
import tablet from './assets/tablet.png'
import airpods from './assets/airpods.png'

function App() {
  return (
    <div className="main">
      <Carousel
        carouselItems={[
          <div className="item">
            <div className="container xPhone">
              <div className="title">xPhone</div>
              <p className="text">Lots to love. Less to spend. Starting at $399</p>
            </div>

            <img src={iphone} alt="" />
          </div>,
          <div className="item">
            <div className="container tablet">
              <div className="title">Tablet</div>
              <p className="text">Just the right amount of everything</p>
            </div>

            <img src={tablet} alt="" />
          </div>,
          <div className="item">
            <div className="container airpods">
              <div className="title">Airpods</div>
              <p className="text">Buy a Tablet or xPhone for college. Get arPods</p>
            </div>

            <img src={airpods} alt="" />
          </div>
        ]}
      />
    </div>
  )
}

export default App
