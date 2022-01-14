import React from 'react'
import Carousel, { CarouselRef } from './components/Carousel'

import airPods from './assets/airpods.png'
import ihpone from './assets/iphone.png'
import tablet from './assets/tablet.png'

function App() {
  const [auto, setAuto] = React.useState(true)
  const carouselRef = React.useRef<CarouselRef>()
  return (
    <>
      <div style={{ display: 'flex', paddingTop: 10, paddingBottom: 10 }}>
        <button
          type="button"
          onClick={() => {
            setAuto(!auto)
          }}
        >
          切换自动播放
        </button>
        <button
          type="button"
          onClick={() => {
            carouselRef.current?.prev()
          }}
        >
          上一页
        </button>
        <button
          type="button"
          onClick={() => {
            carouselRef.current?.next()
          }}
        >
          下一页
        </button>
        <button
          type="button"
          onClick={() => {
            carouselRef.current?.goTo(1)
          }}
        >
          去第2页
        </button>
      </div>
      <Carousel
        ref={(r) => {
          carouselRef.current = r as CarouselRef
        }}
        autoPlay={auto}
        onAfterEnter={(i) => {
          console.log('onAfterEnter', i)
        }}
        onBeforeEnter={(i) => {
          console.log('onBeforeEnter', i)
        }}
      >
        <div className="content-item ihpone">
          <div className="content">
            <div className="header">xPhone</div>
            <div className="text">
              Lots to Love. Less to spend
              <br />
              Starting at $399.
            </div>
          </div>
          <img src={ihpone} alt="" />
        </div>
        <div className="content-item tablet">
          <div className="content">
            <div className="header">Tablet</div>
            <div className="text">Just the right amount of everything</div>
          </div>
          <img src={tablet} alt="" />
        </div>
        <div className="content-item airPods">
          <div className="content">
            <div className="header">
              Buy a Table or xPhone for college
              <br />
              Get arPods
            </div>
          </div>
          <img src={airPods} alt="" />
        </div>
      </Carousel>
    </>
  )
}
export default App
