import React from 'react'
import Carousel, { CarouselItem, CarouselInfo } from './components/Carousel'
import './App.css'
import iphoneImg from './assets/iphone.png'
import tabletImg from './assets/tablet.png'
import airpodsImg from './assets/airpods.png'
export interface IinfoItem {
  id: string
  title1?: string
  title2?: string
  content1?: string
  content2?: string
  img: string
  bgc: string
  fontColor?:string
}
//carousel info
const info: Array<IinfoItem> = [
  {
    id: '1',
    title1: 'xPhone',
    content1: 'Lots to love.Less to spend.',
    content2: 'Staring at $399.',
    img: iphoneImg,
    bgc: '#000',
    fontColor:'#fff'
  },
  {
    id: '2',
    title1: 'Tablet',
    content1: 'Just the right amount of everything.',
    img: tabletImg,
    bgc: '#fff',
  },
  {
    id: '3',
    title1: 'Buy a Tablet or xPhone for college',
    title2: 'Get airPods',
    img: airpodsImg,
    bgc: '#ddd',
  },
]
function App() {
  return (
    <div className='App'>
      <Carousel>
        {info?.map((item) => {
          return (
            <CarouselItem key={item.id} style={{ backgroundColor: item.bgc,color:item.fontColor}}>
              <CarouselInfo
                title1={item.title1}
                title2={item.title2}
                content1={item.content1}
                content2={item.content2}
                img={item.img}
              />
            </CarouselItem>
          )
        })}
      </Carousel>
    </div>
  )
}

export default App
