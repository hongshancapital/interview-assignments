import { ReactNode } from 'react'
import Carousel from './components/Carousel'

import iphone from './assets/iphone.png'
import tablet from './assets/tablet.png'
import airpods from './assets/airpods.png'

import './App.scss'

interface ItemType {
  banner: ReactNode
  img: string
}

const listData: ItemType[] = [
  {
    banner: (
      <div className='banner banner-1'>
        <p>xPhone</p>
        <p>Lots to love. Less to spend.</p>
        <p>Starting at $399.</p>
      </div>
    ),
    img: iphone,
  },
  {
    banner: (
      <div className='banner banner-2'>
        <p>Tablet</p>
        <p>Just the right amount of everything.</p>
      </div>
    ),
    img: tablet,
  },
  {
    banner: (
      <div className='banner banner-3'>
        <p>Buy a Tablet or xPhone for college.</p>
        <p>Get arPods.</p>
      </div>
    ),
    img: airpods,
  },
]

function App() {
  return (
    <div className='App'>
      <Carousel
        className='carousel-full'
        data={listData}
        renderItem={(item) => {
          return (
            <div
              className='carousel-item-content'
              style={{
                backgroundImage: `url(${item.img})`,
              }}
            >
              {item.banner}
            </div>
          )
        }}
      />
    </div>
  )
}

export default App
