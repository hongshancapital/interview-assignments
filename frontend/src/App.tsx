import type { CarouselItemType } from './components/Carousel/types'

import Carousel from './components/Carousel';

import './App.css';

const carouselData: CarouselItemType[] = [
  {
    id: 1,
    name: 'xPhone',
    describe: 'Lots to love.Less to spend.\nStarting at $399.',
    image: require('./assets/iphone.png'),
    background: "#111",
    theme: 'light'
  },
  {
    id: 2,
    name: 'Tablet',
    describe: 'Just the right amount of everything.',
    image: require('./assets/tablet.png'),
    background: "#fafafa",
    theme: 'dark'
  },
  {
    id: 3,
    name: 'Buy a Tablet or xPhone for college.\nget airPods',
    image: require('./assets/airpods.png'),
    background: "#f1f1f3",
    theme: 'dark'
  }
]

function App() {
  return (
    <div className='App'>
      <Carousel carouselData={carouselData} />
    </div>
  )
}

export default App;
