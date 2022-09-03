import React, { FC } from 'react'
import { useSliderIndex } from './hooks'
import { CarouselProps } from './types'

import './index.css'


export const Carousel: FC<CarouselProps> = (props) => {
  const { carouselItems, speed } = props
  const focusIndex = useSliderIndex(carouselItems.length, speed)

  return (
    <div className='scroller'>
      <div
        className='line-bar'
        style={{
          width: `${carouselItems.length * 100}%`,
          transform: `translateX(-${100 * focusIndex / carouselItems.length}%)`
          }}>
        {
          carouselItems.map(it => {
            return (
              <div key={it.src} className="item-div" style={{width: `${100 / carouselItems.length}%`}}>
                <img key={it.src} alt="" src={it.src} />

                <div className="text">
                  { it.titles?.map(title => <div key={title} className="text-title" style={{color: it.textColor || '#000000'}}>{title}</div>) }
                  { it.subTexts?.map(sub => <div key={sub} className="text-sub" style={{color: it.textColor || '#000000'}}>{sub}</div>) }

                </div>

              </div>
            )
          })
        }
      </div>
      <div className="foucus">
        <div className="focus-wrapper">
          {
            carouselItems.map((it, index) => {
              console.log('index = ', index, 'focusIndex = ', focusIndex)
              return (
                <div className="focus-item" key={it.src}>
                {
                  index <= focusIndex
                    ? <div className="focus-fill" style={{animation: `move ${speed}ms linear`}}></div>
                    : null
                }
                </div>
              )
            })
          }
        </div>
      </div>
    </div>
  )
}