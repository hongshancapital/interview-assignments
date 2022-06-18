import React from 'react'
import Carousel, { CarouselItem } from './Components/Carousel'
import './App.css'
import IPhone from './assets/iphone.png'
import Tablet from './assets/tablet.png'
import Airpods from './assets/airpods.png'

const CarouselConfig = [
  {
    title: ['xPhone'],
    subTitle: ['Lots to love. Less to spend.', 'Starting ar $399.'],
    alt: 'iPhone',
    img: IPhone,
    color: 'white',
    backgroundColor: '#101010',
    scale: 0.5
    // link
  },
  {
    title: ['Tablet'],
    subTitle: ['Just the right amount of everything.'],
    alt: 'tablet',
    img: Tablet,
    backgroundColor: '#fafafa',
  },
  {
    title: ['Buy a Tablet or xPhone for college.', 'Get arPods.'],
    alt: 'airpods',
    img: Airpods,
    backgroundColor: '#f2f2f3',
    scale: 1.3
  }
]

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
