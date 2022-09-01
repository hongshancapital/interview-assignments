import React, {useMemo, ReactNode} from "react";
import {IndicatorProps} from './interface'
// import './styles.css'

function Indicator(props: IndicatorProps){
  const {activeIndex, delay, length, onDotClick} = props
  const nodeList = useMemo(()=>{
    const IndicatorNodes:ReactNode[] = []
    for(let i= 0; i < length; i++){
      IndicatorNodes.push(
        <span 
          role="indicator"
          className={`indicator-span ${activeIndex === i ? 'active' : ''}`} 
          key={i}
          onClick={() => {
            onDotClick && onDotClick(i)
          }}
        >
          <i style={{animationDuration: (activeIndex === i ? delay : 0) + 'ms'} }></i>
        </span>
      )
    }
    return IndicatorNodes
  }, [activeIndex, delay, length])

  return (
    <div className='indicator-wrap'>
      {nodeList}
    </div>
  )
}

export default Indicator;
