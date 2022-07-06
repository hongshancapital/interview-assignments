import React from 'react'
import Carousel, { CarouselItem } from './Components/Carousel'
import { CarouselConfig } from './const'
import './App.css'

function getScaleStyle(scale: number): React.CSSProperties {
  return {
    width: `${scale * 100}%`,
    margin: `0 ${(1 - scale) / 2 * 100}%`
  }
}

function App () {
  return (<div className="App">
    <Carousel>
      {
        CarouselConfig.map(({ title = [], subTitle = [], alt, img, color, backgroundColor, scale }) => (
          // 由于使用了背景色，这里没必要对图片做懒加载
          <CarouselItem key={alt}>
            <div
              className="banner-container"
              style={{backgroundColor}}
            >
              <div className="banner-text-con" style={{ color }}>
                {title.map((str, i) => <div className="title" key={i}>{str}</div>)}
                {subTitle.map((str, i) => <div className="text" key={i}>{str}</div>)}
              </div>
              <img alt={alt} style={scale ? getScaleStyle(scale) : {}} src={img} />
            </div>
          </CarouselItem>
        ))
      }
    </Carousel>
  </div>)
}

export default App
