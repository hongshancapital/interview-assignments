import React from 'react'
import { render, screen } from '@testing-library/react'
import Carousel, { CarouselInfo, CarouselItem, CarouselProps } from './index'
import { IinfoItem } from '../../App'
import iphoneImg from '../../assets/iphone.png'
import tabletImg from '../../assets/tablet.png'
const testCarouselProps: CarouselProps = {
  delay: 5000,
  children: '',
}
const info: Array<IinfoItem> = [
  {
    id: '1',
    title1: 'xPhone',
    content1: 'Lots to love.Less to spend.',
    content2: 'Staring at $399.',
    img: iphoneImg,
    bgc: '#000',
    fontColor: '#fff',
  },
  {
    id: '2',
    title1: 'Tablet',
    content1: 'Just the right amount of everything.',
    img: tabletImg,
    bgc: '#fff',
  },
]
const testCarousel = (props: CarouselProps) => {
  return (
    <Carousel {...props}>
      {info?.map((item) => {
        return (
          <CarouselItem
            key={item.id}
            style={{ backgroundColor: item.bgc, color: item.fontColor }}
          >
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
  )
}
describe('2 simple tests on the carousel component', () => {
  const setup = () => {
    render(testCarousel(testCarouselProps))
  }
  it('should have 2 imgs', () => {
    setup()
    expect(screen.getAllByTitle('img').length).toEqual(2)
  })
  it('the second img is Visible after 5000ms', () => {
    setup()
    setTimeout(()=>{
      expect(screen.getAllByTitle('img')[1]).toBeVisible()
    },5000)
  })
})
