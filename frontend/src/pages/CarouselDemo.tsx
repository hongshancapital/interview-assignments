import React from 'react'
import Carousel, { CarouselSlideProps } from '../components/carousel'
import IconAirpods from '../assets/airpods.png'
import IconIphone from '../assets/iphone.png'
import IconTablet from '../assets/tablet.png'

const data: Array<CarouselSlideProps> = [
  {
    index: 0,
    title: 'xPhone',
    desc: 'Lots to love. Less to spend. \nStarting at $399.',
    style: {
      backgroundColor: 'rgb(17, 17, 17)',
      backgroundImage: `url(${IconIphone})`,
      color: '#fff',
    },
  },
  {
    index: 1,
    title: 'Tablet',
    desc: 'Just the right amount of everything.',
    style: {
      backgroundColor: 'rgb(248, 248, 248)',
      backgroundImage: `url(${IconTablet})`,
      color: '#000',
    },
  },
  {
    index: 2,
    title: 'Buy a tablet or xPhone for college. \nGet arpods.',
    desc: '',
    style: {
      backgroundColor: 'rgb(241, 241, 243)',
      backgroundImage: `url(${IconAirpods})`,
    },
  },
]

export default function CarouselDemo() {
  return <Carousel data={data} />
}
