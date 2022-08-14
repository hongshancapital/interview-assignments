import React from "react";
import "./App.css";
import Carousel, { CarouselItem } from './components/Carousel'
import iphoneImgBg from './assets/iphone.png'
import tabletImgBg from './assets/tablet.png'
import airpodsImgBg from './assets/airpods.png'

function App() {
  return (
    <div className="App">
      <Carousel>
          <CarouselItem>
              <div className="ppt item1">
                <div className="ppt-content">
                  <h1>xPhone</h1>
                  <p>Lots to love. Less to spend</p>
                  <p>Starting at $399.</p>
                </div>
                <img src={iphoneImgBg} alt="图片加载失败" className="ppt-img"/>
              </div>
          </CarouselItem>
          <CarouselItem>
              <div className="ppt item2">
                <div className="ppt-content">
                  <h1>Tablet</h1>
                  <p>Just the right amount of everything</p>
                </div>
                <img src={tabletImgBg} alt="图片加载失败" className="ppt-img"/>
              </div>
          </CarouselItem>
          <CarouselItem>
              <div className="ppt item3">
                <div className="ppt-content">
                  <h1>Buy a Tablet or xPhone for college</h1>
                  <h1> Get arPods</h1>
                </div>
                <img src={airpodsImgBg} alt="图片加载失败" className="ppt-img"/>
              </div>
          </CarouselItem>
      </Carousel>
    </div>
  )
}

export default App;
