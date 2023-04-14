import Carousel from './components/Carousel/index'
import imgIPhone from './assets/iphone.png'
import imgTablet from './assets/tablet.png'
import imgAirPods from './assets/airpods.png'

import './App.css'

export interface CarouselDataItem{
  id: string
  color: string
  backgroundColor: string
  title: string[]
  contents?: string[]
  bg: string
}


const data: CarouselDataItem[] = [
  {
    id: '1',
    color: '#fff',
    backgroundColor: '#111111',
    title: ['xPhone'],
    contents: ['Lots to love. Less to spend.', 'Starting at $399.'],
    bg: imgIPhone
  },
  {
    id: '2',
    color: '#000',
    backgroundColor: '#FAFAFA',
    title: ['Tablet'],
    contents: ['Just the right amount of everything.'],
    bg: imgTablet
  },
  {
    id: '3',
    color: '#000',
    backgroundColor: '#F1F1F1',
    title: ['Buy a Tablet or xPhone for college.', 'Get airPods.'],
    bg: imgAirPods
  }
]

function App() {
  return (
    <div className="App">
      <Carousel
        items={data}
      />
    </div>
  )
}

export default App;