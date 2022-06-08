import React, { useCallback } from 'react';
import '../styles/IndicatorLines.css'

type Prop ={
    itemsCount:number;
    activeIndex:number;
    onAnimationEnd:()=>void;
}

function IndicatorLines({itemsCount,activeIndex,onAnimationEnd} :Prop) {
    const isActive = useCallback(
        (index)=>index === activeIndex,[activeIndex]
    )
  return (
    <div className='indicator-container'>
        {[...Array(itemsCount)].map((_,index)=>index).map((currentIndex) => (
            <div className='indicator-item' key = {currentIndex}>
                <div className={['indicator-mask',isActive(currentIndex) ? 'indicator-mask-active' : ''].join(' ')}
                onAnimationEnd = {onAnimationEnd} 
                />
            </div>
        ))}
    </div>
  )
}
export default IndicatorLines;
