import React, {useRef} from 'react'

import "./index.css"

type CarouselIndicatorsProps = {
  current: number,
  count: number,
  autoPlay: boolean,
  interval: number,
  onChange: (index: number) => void
}

const Indicators = (props: CarouselIndicatorsProps) => {
  const {current, count, onChange, autoPlay, interval} = props
  const indicatorsRef = useRef<HTMLDivElement>(null)

  if(count <= 1) {
    return null
  }

  return (<div className="carousel__indicators" ref={indicatorsRef}>
    {new Array(count).fill(true).map((_, index) => 
      <div 
        key={index}
        onClick={() => onChange(index)}
        className={current === index
          ? 'carousel__indicator carousel__indicator-active' 
          : 'carousel__indicator'}>
            {autoPlay 
              ? <div className="indicator__progress" style={{animationDuration: `${interval}ms`}}></div>
              : <div className="indicator__content"></div>
            }
          </div>
    )}
  </div>)
}

export default Indicators