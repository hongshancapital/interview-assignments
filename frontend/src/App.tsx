import React from "react"
import "./App.css"
import { Carousel } from './components/carousel'
import img1 from './assets/iphone.png'
import img2 from './assets/tablet.png'
import img3 from './assets/airpods.png'
import { iItemProps } from './components/carousel/types'
const carouselItems: iItemProps[] = [{
  src: img1,
  titles: ['xPhone'],
  subTexts: ['Lots to love.Less to spend', 'Starting at $399'],
  textColor: '#FFFFFF'
}, {
  src: img2,
  titles: ['Tablet'],
  subTexts: ['Just the right amout of everything.']
}, {
  src: img3,
  titles: ['Buy a Tablet or xPhone for college', 'Get airPlods.'],
  subTexts: []
}]
function App() {
  return <div className="App">
    <Carousel carouselItems={carouselItems} speed={3000} />
  </div>
}

export default App;
