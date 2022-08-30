import React from 'react'

import Carousel from './components/carousel'
import type { CarouselItemProps } from './components/carousel'

import './App.css'

function App() {
  const carouselItems: CarouselItemProps[] = [
    {
      title: 'xPhone',
      desc: 'Lots to love. Less to spend.\nStarting at $399.',
      bgImg: '/iphone.png',
      bgColor: '#111111',
    },
    {
      title: 'Tablet',
      desc: 'Just the right amount of everything.',
      bgImg: '/tablet.png',
      bgColor: '#fafafa',
    },
    {
      title: 'Buy a Tablet or Phone for college.\nGet arPods.',
      bgImg: '/airpods.png',
      bgColor: '#f1f1f3',
    },
  ]
  return (
    <div className="App">
      <Carousel items={carouselItems} />
    </div>
  )
}

export default App
