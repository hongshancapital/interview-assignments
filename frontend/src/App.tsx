import React from 'react'
import './App.css'
import Carousel, { CarouselItem } from './components/carousel/Carousel'
import iphone from './assets/iphone.png'
import tablet from './assets/tablet.png'
import airpods from './assets/airpods.png'

export default function App() {
  const data: Array<CarouselItem> = [
    { title: 'xPhone', desc: ['Lots to love.Less to spend.', 'Starting at $399.'], img: iphone, color: '#fff', bgColor: '#111' },
    { title: 'Tablet', desc: 'Just the right amount of everything.', img: tablet, bgColor: '#fafafa' },
    { title: ['Buy a Tablet or xPhone for college.', 'Get arPods.'], img: airpods, bgColor: '#f1f1f3' },
  ]

  return (
    <Carousel data={data} />
  )
}
