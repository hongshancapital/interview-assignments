import React from 'react'
import './index.css'

export interface GoodsCardProps {
  headline: string
  subhead?: string
  image: string
  backgroundColor?: string
  color: string
}

export default function GoodsCard ({ headline, subhead, image, backgroundColor, color }: GoodsCardProps) {
  return <section className='goods-card' style={{ backgroundColor, color }}>
    <div className='goods-card-wrap'>
      <div className='goods-card-content'>
        <div className='goods-card-info'>
          <h2 className='headline'>{headline}</h2>
          {subhead && <h3 className='subhead'>{subhead}</h3>}
        </div>
        <div className='image-wrap'>
          <figure style={{ backgroundImage: `url(${image})` }}></figure>
        </div>
      </div>
    </div>
  </section>
}
