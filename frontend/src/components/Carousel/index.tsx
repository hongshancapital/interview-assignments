import React from 'react'
import Panel from './components/Panel'
import Slider from './components/Slider'
import { CarouselProps } from './Options'
import useCarousel from './hooks/useCarousel'
import './index.css'

function Carousel(props: CarouselProps): React.ReactElement {
  const {
    index,
    total,
    data,
    duration,
    refWrap,
    onTransitionEnd
  } = useCarousel(props)

  return (
    <div className="carousel">
      <div className="panel-wrap" ref={refWrap}>
        {
          data.map((item, index) => {
            return <Panel key={index} data={item} />
          })
        }
      </div>
      <Slider count={total} current={index} duration={duration} onTransitionEnd={onTransitionEnd} />
    </div>
  )
}

export default React.memo(Carousel)
