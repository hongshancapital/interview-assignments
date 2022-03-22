import React, {forwardRef, useState, useImperativeHandle, useCallback, useEffect} from 'react'
import { Carousel as AntdCarousel } from 'antd';
import './index.scss'

export interface CarouselProps {
  children?: React.ReactNode
  dots?: boolean
}

export interface CarouselRef {
  go: (index: number) => void;
  next: () => void;
  prev: () => void
}

const Carousel = forwardRef<CarouselRef, CarouselProps>((CarouselProps, ref) => {
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

  return (
    <AntdCarousel
      // autoplay
      dotPosition="bottom"
    >
      {
        ads.map(ad => (
          <div className={`ad bg ${ad.bg}`} key={ad.key}>
            <div className="ad__title">{ ad.title }</div>
            <div className="ad__introduce">{ ad.introduce }</div>
          </div>
        ))
      }
    </AntdCarousel>
  )
})

export default Carousel
