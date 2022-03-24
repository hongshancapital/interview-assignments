import React from 'react'
import './index.scss'
import Carousel from '../../components/Carousel'
import CarouselItem, { ICarouselItemProps } from '../../components/CarouselItem'
import { mockData } from '../../model'

function App() {
  return (
    <div className="App">
      <Carousel autoplay={true} autoPlayInterval={3000}>
        {mockData.map((item: ICarouselItemProps) => {
          return <CarouselItem {...item} key={item.key} />
        })}
      </Carousel>
    </div>
  )
}

export default App
