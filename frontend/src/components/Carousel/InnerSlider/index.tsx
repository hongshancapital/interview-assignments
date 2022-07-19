import React, { ReactElement } from 'react'

import './index.css'

export interface InnerSliderProps {
  length: number
  currentIndex: number
  animationEnd: () => void
}

export default function InnerSlider({
  length,
  currentIndex,
  animationEnd,
}: InnerSliderProps): ReactElement {
  return (
  <ul className='inner_slider'>
    {
      new Array(length).fill(true).map((n, index) => {
        return (
          <li 
            className={`inner_slider_li ${currentIndex === index ? 'inner_slider_li_anim' : ''}`}
            onAnimationEnd={animationEnd}
            key={index}
          ></li>
        )
      })
    }
  </ul>
  )
}