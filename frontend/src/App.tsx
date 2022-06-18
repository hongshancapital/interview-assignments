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
    color: 'white'
    // link
  },
  {
    title: ['Tablet'],
    subTitle: ['Just the right amount of everything.'],
    alt: 'tablet',
    img: Tablet,
    color: 'black'
  },
  {
    title: ['Buy a Tablet or xPhone for college.', 'Get arPods.'],
    subTitle: [],
    alt: 'airpods',
    img: Airpods,
    color: 'black'
  }
]

const PR = 1 / window.devicePixelRatio

function App () {
  return (<div className="App">
    <Carousel>
      {
        CarouselConfig.map(({ title, subTitle, alt, img, color }) => (
          <CarouselItem key={alt}>
            <div className="banner-container">
              <div className="banner-text-con" style={{ color }}>
                {title.map((str, i) => <div className="title" key={i}>{str}</div>)}
                {subTitle.map((str, i) => <div className="text" key={i}>{str}</div>)}
              </div>
              <img alt={alt} src={img} data-pr={PR}/>
            </div>
          </CarouselItem>
        ))
      }
    </Carousel>
  </div>)
}

export default App
