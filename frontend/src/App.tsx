import React from 'react'
import './App.css'
import { Carousel, CarouselItem } from './components/Carousel'

function App() {
  return (
    <div className="App">
      <Carousel style={{ height: '100vh' }} autoplay={true}>
        <CarouselItem
          content={{
            title: 'xPhone',
            subtitle: 'Lots to Love. Less to spend.\nStarting at $399.',
            color: '#ffffff',
            background: require('./assets/iphone.png'),
            backgroundColor: '#111111'
          }}
        />
        <CarouselItem
          content={{
            title: 'Tablet',
            subtitle: 'Just the right amount of everything.',
            color: '#000000',
            background: require('./assets/tablet.png'),
            backgroundColor: '#fafafa'
          }}
        />
        <CarouselItem
          content={{
            title: 'Buy a Tablet or xPhone for college.\nGet arPods.',
            color: '#000000',
            background: require('./assets/airpods.png'),
            backgroundColor: '#f1f1f3'
          }}
        />
      </Carousel>
    </div>
  )
}

export default App
