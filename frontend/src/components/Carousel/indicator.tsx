import React, {useMemo} from "react";
import {IndicatorProps} from './interface'

function Indicator(props: IndicatorProps){
  const {activeIndex, delay, length, onDotClick} = props

  // 根据props传入数量生成相等数量的数组，来进行map
  const DotList = useMemo(()=>{
    return new Array(length).fill('')
  }, [length])
  
  return (
    <div className='indicator-wrap'>
      {
        DotList.map((_, index) => (
          <span 
            className={`indicator-span ${activeIndex === index ? 'active' : ''}`} 
            key={index}
            onClick={() => {
              onDotClick && onDotClick(index)
            }}
          >
            <i style={{animationDuration: (activeIndex === index ? delay : 0) + 'ms'} }></i>
          </span>
        ))
      }
    </div>
  )
}

export default Indicator;
