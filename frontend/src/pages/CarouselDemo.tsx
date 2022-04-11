import React from 'react'
import { Carousel, CarouselSlide } from '../components/carousel'
import IconAirpods from '../assets/airpods.png'
import IconIphone from '../assets/iphone.png'
import IconTablet from '../assets/tablet.png'
import './CarouselDemo.css'

const data: Array<any> = [
  {
    key: 0,
    title: 'xPhone',
    desc: 'Lots to love. Less to spend. \nStarting at $399.',
    style: {
      backgroundColor: 'rgb(17, 17, 17)',
      backgroundImage: `url(${IconIphone})`,
      color: '#fff',
    },
  },
  {
    key: 1,
    title: 'Tablet',
    desc: 'Just the right amount of everything.',
    style: {
      backgroundColor: 'rgb(248, 248, 248)',
      backgroundImage: `url(${IconTablet})`,
      color: '#000',
    },
  },
  {
    key: 2,
    title: 'Buy a tablet or xPhone for college. \nGet arpods.',
    desc: '',
    style: {
      backgroundColor: 'rgb(241, 241, 243)',
      backgroundImage: `url(${IconAirpods})`,
    },
  },
]

export default function CarouselDemo() {
  return (
    <Carousel duration={3000} flashNext={500} flashStart={300}>
      {data.map((slide: any) => (
        <CarouselSlide key={slide.key}>
          <div className="carousel-slide" style={slide.style}>
            <div className="carousel-slide__title">{slide.title}</div>
            <div className="carousel-slide__desc">{slide.desc}</div>
          </div>
        </CarouselSlide>
      ))}
    </Carousel>
  )
}
