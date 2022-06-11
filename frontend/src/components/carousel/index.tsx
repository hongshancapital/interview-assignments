import React, { FC } from 'react'
import { useSliderIndex } from './hooks'
import './index.css'

interface CarouselProps {
  imgs: Array<string>
  speed: number
}

export const Carousel: FC<CarouselProps> = (props) => {
  const { imgs, speed } = props
  const focusIndex = useSliderIndex(imgs.length, speed)

  return (
    <div className='scroller'>
      <div
        className='line-bar'
        style={{
          width: `${imgs.length * 100}%`,
          transform: `translateX(-${100 * focusIndex / imgs.length}%)`
          }}>
        {
          imgs.map(src => {
            return (
              <div key={src} className="img-div" style={{width: `${100 / imgs.length}%`}}>
                <img key={src} alt="" src={src} />
              </div>
            )
          })
        }
      </div>
      <div className="foucus">
        <div className="focus-wrapper">
          {
            imgs.map((src, index) => {
              console.log('index = ', index, 'focusIndex = ', focusIndex)
              return (
                <div className="focus-item" key={src}>
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