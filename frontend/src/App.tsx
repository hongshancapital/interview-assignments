import './App.css'
import { useCallback } from 'react'
import Carousel from './components/Carousel/index.tsx'
import AirPods from './assets/airpods.png'
import iPhone from './assets/iphone.png'
import Tablet from './assets/tablet.png'
import { ICarouselData } from './type'
import CarouselItem from './components/CustomCarouselItem/index.tsx'

const CAROUSEL_DATA: ICarouselData[] = [
  {
    title: 'xPhone',
    subtitle: 'Lots to love. Less to spend. Starting at $399.',
    icon: iPhone,
  },
  {
    title: 'Tablet',
    subtitle: 'Just the right amount of everything',
    icon: Tablet,
  },
  {
    title: 'Buy a Tablet or xPhone for college.',
    subtitle: 'Get arPods.',
    icon: AirPods,
  },
]

function App() {
  const renderCarouselItem = useCallback((itemData: ICarouselData, index: number) => {
    return <CarouselItem data={itemData} key={index} color={index === 0 ? '#fff' : '#000'}></CarouselItem>
  }, [])

  return (
    <div className="App">
      <Carousel data={CAROUSEL_DATA} renderItem={renderCarouselItem}></Carousel>
    </div>
  )
}

export default App
