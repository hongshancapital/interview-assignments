import React, { forwardRef, useImperativeHandle, useRef } from "react"
import { Carousel as AntdCarousel } from "antd"
import './index.scss'

export interface CarouselProps {
  autoplay?: boolean
}

export interface CarouselRef {
  go: (index: number) => void;
  next: () => void;
  prev: () => void
}

const Carousel = forwardRef<CarouselRef, CarouselProps>(({ autoplay }, ref) => {
  const ads = [
    {
      key: 'xPhone',
      title: 'xPhone',
      introduce: 'lots to love Less to spend Starting ar $3999',
      bg: 'bg-iphone',
      class: 'ad-xphone'
    },
    {
      key: 'tablet',
      title: 'Tablet',
      introduce: 'Just the right amount of everything',
      bg: 'bg-tablet',
      class: 'ad-tablet'
    },
    {
      key: 'airPods',
      title: 'Buy a Tablet or xPhone for college Get airPods',
      introduce: '',
      bg: 'bg-airpods',
      class: 'ad-airpods'
    }
  ]

  const carouselRef = useRef<any>()

  useImperativeHandle(ref,() => carouselRef?.current, [carouselRef?.current])

  return (
    <AntdCarousel
      autoplay={autoplay}
      dotPosition="bottom"
      className="carousel"
      ref={carouselRef}
    >
      {
        ads.map(ad => (
          <div
            key={ad.key}
            className={`ad bg ${ad.bg} ${ad.class}`}
          >
            <div className="ad__title">{ ad.title }</div>
            <div className="ad__introduce">{ ad.introduce }</div>
          </div>
        ))
      }
    </AntdCarousel>
  )
})

export default Carousel
