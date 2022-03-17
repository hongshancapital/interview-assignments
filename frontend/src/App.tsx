import React from 'react'

import Carousel from './components/carousel'

import airpords from './assets/airpods.png'
import iphone from './assets/iphone.png'
import tablet from './assets/tablet.png'
import './App.css'

const mockDataList = [
  {
    title: 'xPhone',
    desc: 'this is a xPhone description',
    img: iphone,
    className: 'font--white'
  },
  {
    title: 'Tablet',
    desc: 'this is a Tablet description',
    img: tablet,
    className: 'font--black'
  },
  {
    title: 'Buy xPhone or Tablet',
    desc: '',
    img: airpords,
    className: 'font--black'
  }
]

function App() {
  return (
    <div className='App'>
      <Carousel duration={5000}>
        {mockDataList.map(({ title, desc, img, className }) => (
          <div className='item' key={title}>
            <div className='item__content'>
              <h1 className={className}>{title}</h1>
              {!!desc && <p className={className}>{desc}</p>}
            </div>
            <img src={img} className='item__img' alt={title} />
          </div>
        ))}
      </Carousel>
    </div>
  )
}

export default App
