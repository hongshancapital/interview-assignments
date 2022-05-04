import React from 'react'
import './App.scss'
import Carousel from '../../lib/src'
import IPHONE from './assets/iphone.png'
import TABLET from './assets/tablet.png'
import AIRPODS from './assets/airpods.png'

function App() {
  return (
    <div className="App">
      <Carousel duration={3000}>
        <div className="page page1">
          <section>
            <h1>
              xPhone
            </h1>
            <p>Lots to Love. Less to spend.</p>
            <p>Staring at $399.</p>
          </section>
          <img src={IPHONE} alt="iphone" />
        </div>
        <div className="page page2">
          <section>
            <h1>
              Tablet
            </h1>
            <p>Just the right amount of everything.</p>
          </section>
          <img src={TABLET} alt="tablet" />
        </div>
        <div className="page page2">
          <section>
            <h1>
              Buy a Tablet or xPhone for College.
            </h1>
            <h1>
              Get a airPods.
            </h1>
          </section>
          <img src={AIRPODS} alt="airpods" />
        </div>
      </Carousel>
    </div>
  )
}

export default App
