import React from 'react'
import Carousel from './components/carousel/index'
import './App.css'
import { carouselList } from './mock/carousel'

function App() {
  return (
    <div className="App">
      <Carousel speed={3000} direction="horizontal" carouselList={carouselList} initialPassTime={1000} />
    </div>
  )
}

export default App
