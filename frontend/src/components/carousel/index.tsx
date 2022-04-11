import React from 'react'
import './index.css'

export interface CarouselSlideProps {
  index: number
  title: string
  desc: string
  style?: object
}

interface CarouselProps {
  data: Array<CarouselSlideProps>
}

const Carousel = (props: CarouselProps) => {
  const { data } = props
  return (
    <div className="carousel-container">
      <div className="carousel" style={{ width: `${data.length * 100}%` }}>
        {data.map((slide: CarouselSlideProps) => (
          <div className="carousel-slide" key={slide.index} style={slide.style}>
            <div className="carousel-slide__title">{slide.title}</div>
            <div className="carousel-slide__desc">{slide.desc}</div>
          </div>
        ))}
      </div>
      <div className="carousel-indicator">
        <div></div>
        <div></div>
        <div></div>
      </div>
    </div>
  )
}

export default Carousel
